package com.e.bambi.inventory.application.offer.handler.command.message;

import com.e.bambi.inventory.application.offer.dto.command.message.CancelReservationInventoryCommand;
import com.e.bambi.inventory.application.offer.port.outbound.repository.OfferRepository;
import com.e.bambi.inventory.application.outbox.InventoryOutboxEventHelper;
import com.e.bambi.inventory.domain.event.InventoryAggregateType;
import com.e.bambi.inventory.domain.event.InventoryReservationCancelledEvent;
import com.e.bambi.inventory.domain.exception.OfferNotFoundException;
import com.e.bambi.shared.kernel.application.bus.CommandHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.e.bambi.shared.kernel.application.saga.order.SagaConstants.ORDER_SAGA_NAME;

@Slf4j
@Component
@RequiredArgsConstructor
public class CancelReservationInventoryCommandHandler implements
        CommandHandler<Mono<Void>, CancelReservationInventoryCommand> {

    private final TransactionalOperator tx;
    private final InventoryOutboxEventHelper inventoryOutboxEventHelper;
    private final OfferRepository offerRepository;

    @Override
    public Mono<Void> handle(CancelReservationInventoryCommand command) {
        return isInventoryOutboxEventProcessed(command.getSagaId())
                .flatMap(existsInventoryOutboxEvent -> {
                    if (existsInventoryOutboxEvent) {
                        log.info("An inventory outbox event with saga id: {} and order id: {} is already processed",
                                command.getSagaId(), command.getOrderId().getValue());
                        return Mono.empty();
                    }

                    log.info("Cancelling products for order id: {} ", command.getOrderId().getValue());
                    return cancelReservation(command)
                            .flatMap(event ->
                                    inventoryOutboxEventHelper
                                            .saveInventoryOutboxEvent(event.getAggregatetype(),
                                                    command.getSagaId(),
                                                    ORDER_SAGA_NAME,
                                                    event.toPayload()
                                            )
                            );
                });
    }

    private Mono<Boolean> isInventoryOutboxEventProcessed(String aggregateid) {
        return inventoryOutboxEventHelper
                .existsInventoryOutboxEventByAggregateidAndAggregateType(aggregateid,
                        InventoryAggregateType.RESERVATION_CANCELLED.getValue());
    }

    private Mono<InventoryReservationCancelledEvent> cancelReservation(CancelReservationInventoryCommand command) {
        return tx.execute(status ->
                        Flux.fromIterable(command.getProducts())
                                .flatMap(product ->
                                        offerRepository.findBySupplierIdAndProductId(
                                                        product.getSupplierId(),
                                                        product.getProductId()
                                                )
                                                .flatMap(offer -> {
                                                    offer.cancelReservation(product.getQuantity());
                                                    return offerRepository.update(offer);
                                                })
                                                .switchIfEmpty(
                                                        Mono.error(new OfferNotFoundException("Product doesn't exists " +
                                                                "for the given supplier id and product id"))
                                                ))
                                .then()
                )
                .then(Mono.defer(() -> {
                    log.info("Products successfully cancelled for order id: {}", command.getOrderId());
                    return Mono.just(new InventoryReservationCancelledEvent(
                            InventoryAggregateType.RESERVATION_CANCELLED.getValue(),
                            command.getOrderId()));
                }));
    }
}
