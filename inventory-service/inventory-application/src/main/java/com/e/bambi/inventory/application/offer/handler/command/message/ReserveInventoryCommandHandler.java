package com.e.bambi.inventory.application.offer.handler.command.message;

import com.e.bambi.inventory.application.offer.dto.command.message.ReserveInventoryCommand;
import com.e.bambi.inventory.application.offer.port.outbound.repository.OfferRepository;
import com.e.bambi.inventory.application.outbox.InventoryOutboxEventHelper;
import com.e.bambi.inventory.domain.event.InventoryAggregateType;
import com.e.bambi.inventory.domain.event.InventoryEvent;
import com.e.bambi.inventory.domain.event.InventoryReservationFailedEvent;
import com.e.bambi.inventory.domain.event.InventoryReservedEvent;
import com.e.bambi.inventory.domain.exception.OfferNotFoundException;
import com.e.bambi.shared.kernel.application.bus.CommandHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.util.List;

import static com.e.bambi.shared.kernel.application.saga.order.SagaConstants.FAILURE_MESSAGE_DELIMITER;
import static com.e.bambi.shared.kernel.application.saga.order.SagaConstants.ORDER_SAGA_NAME;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReserveInventoryCommandHandler implements CommandHandler<Mono<Void>, ReserveInventoryCommand> {

    private final TransactionalOperator tx;
    private final OfferRepository offerRepository;
    private final InventoryOutboxEventHelper inventoryOutboxEventHelper;

    @Override
    public Mono<Void> handle(ReserveInventoryCommand command) {
        return isInventoryOutboxEventProcessed(command.getSagaId())
                .flatMap(existsInventoryOutboxEvent -> {

                    if (existsInventoryOutboxEvent) {
                        log.info("A inventory outbox event whit saga id: {} and order id: {} is already processed",
                                command.getSagaId(), command.getOrderId().getValue());
                        return Mono.empty();
                    }

                    log.info("Reserving products for order id: {}", command.getOrderId().getValue());
                    return validateAndReserve(command)
                            .flatMap(event ->
                                    inventoryOutboxEventHelper.saveInventoryOutboxEvent(
                                            event.getAggregatetype(),
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
                        InventoryAggregateType.RESERVED.getValue(),
                        InventoryAggregateType.RESERVATION_FAILED.getValue());
    }

    private Mono<InventoryEvent> validateAndReserve(ReserveInventoryCommand command) {
        return tx.execute(status ->
                        Flux.fromIterable(command.getProducts())
                                .flatMap(p -> offerRepository
                                        .findBySupplierIdAndProductId(p.getSupplierId(), p.getProductId())
                                        .flatMap(offer -> {
                                            offer.validateAndReserve(p.getPrice(), p.getQuantity());
                                            return offerRepository.update(offer);
                                        })
                                        .retryWhen(Retry.max(Integer.MAX_VALUE).filter(this::isOptimisticLockFailure))
                                        .switchIfEmpty(Mono.error(new OfferNotFoundException(
                                                "Product doesn't exists for the given supplier id and product id")))
                                )
                                .then()
                )
                .then(Mono.defer(() -> {
                            log.info("Products successfully reserved for order id: {}", command.getOrderId().getValue());
                            return Mono.just((InventoryEvent) new InventoryReservedEvent(
                                    InventoryAggregateType.RESERVED.getValue(),
                                    command.getOrderId()));
                        })
                )
                .onErrorResume(e -> {
                            log.info("Error while reserving products for order id: {}", command.getOrderId().getValue());
                            return Mono.just(new InventoryReservationFailedEvent(
                                    InventoryAggregateType.RESERVATION_FAILED.getValue(),
                                    command.getOrderId(),
                                    List.of(e.getMessage().split(FAILURE_MESSAGE_DELIMITER))));
                        }
                );
    }

    private boolean isOptimisticLockFailure(Throwable throwable) {
        return throwable instanceof OptimisticLockingFailureException;
    }
}
