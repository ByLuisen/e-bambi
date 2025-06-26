package com.e.bambi.order.application.order.saga;

import com.e.bambi.order.application.order.OrderApplicationService;
import com.e.bambi.order.application.order.dto.command.message.inventory.ReservationFailedInventoryCommand;
import com.e.bambi.order.application.order.dto.command.message.inventory.ReservedInventoryCommand;
import com.e.bambi.order.application.order.port.outbound.repository.OrderRepository;
import com.e.bambi.order.application.order.port.outbound.repository.OrderStatusHistoryQueryRepository;
import com.e.bambi.order.application.outbox.OrderOutboxEventHelper;
import com.e.bambi.order.domain.event.OrderAggregateType;
import com.e.bambi.order.domain.OrderDomainService;
import com.e.bambi.order.domain.event.OrderPaymentValidateEvent;
import com.e.bambi.order.domain.exception.OrderNotFoundException;
import com.e.bambi.order.domain.order.valueobject.OrderStatus;
import com.e.bambi.shared.kernel.application.saga.SagaStatus;
import com.e.bambi.shared.kernel.application.saga.SagaStep;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

import static com.e.bambi.shared.kernel.application.saga.order.SagaConstants.ORDER_SAGA_NAME;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderInventorySaga implements SagaStep<ReservedInventoryCommand, ReservationFailedInventoryCommand> {

    private final OrderRepository orderRepository;
    private final OrderDomainService orderDomainService;
    private final OrderOutboxEventHelper orderOutboxEventHelper;
    private final OrderApplicationService orderApplicationService;
    private final OrderStatusHistoryQueryRepository orderStatusHistoryQueryRepository;

    @Override
    @Transactional
    public Mono<Void> process(ReservedInventoryCommand data) {
        return orderStatusHistoryQueryRepository
                .existsByOrderIdAndOrderStatus(data.getOrderId(), OrderStatus.PRODUCTS_RESERVED)
                .flatMap(exists -> {
                    if (exists) {
                        log.info("Products for order id: {} and order status: {} is already reserved",
                                data.getOrderId().getValue(), OrderStatus.PRODUCTS_RESERVED);
                        return Mono.empty();
                    }

                    log.info("Confirming products reservation for order id: {}", data.getOrderId().getValue());
                    return confirmOrderReservation(data)
                            .flatMap(event -> {
                                SagaStatus sagaStatus =
                                        orderApplicationService.orderStatusToSagaStatus(event.getOrder().getOrderStatus());

                                return orderOutboxEventHelper
                                        .saveOrderOutboxEvent(
                                                event.getAggregatetype(),
                                                event.toPayload(),
                                                data.getSagaId(),
                                                ORDER_SAGA_NAME,
                                                sagaStatus
                                        ).flatMap(orderOutboxEventId -> {
                                            log.info("Products for order id: {} is confirmed",
                                                    data.getOrderId().getValue());

                                            return orderOutboxEventHelper.deleteOrderOutboxEvent(orderOutboxEventId);
                                        });
                            });
                });
    }

    @Override
    @Transactional
    public Mono<Void> rollback(ReservationFailedInventoryCommand data) {
        return orderStatusHistoryQueryRepository.existsByOrderIdAndOrderStatus(data.getOrderId(), OrderStatus.CANCELLED)
                .flatMap(exists -> {
                    if (exists) {
                        log.info("An order id: {} and order status: {} is already roll backed!",
                                data.getOrderId().getValue(), OrderStatus.CANCELLED);
                        return Mono.empty();
                    }

                    return rollbackOrder(data);
                });
    }

    private Mono<OrderPaymentValidateEvent> confirmOrderReservation(ReservedInventoryCommand data) {
        log.info("Confirming order products for order id: {}", data.getOrderId().getValue());
        return orderApplicationService.findOrder(data.getOrderId())
                .flatMap(order -> {
                    OrderPaymentValidateEvent event =
                            orderDomainService
                                    .confirmOrderReservation(OrderAggregateType.PAYMENT_VALIDATE.getValue(), order);

                    return orderRepository.update(order)
                            .thenReturn(event);
                });
    }

    private Mono<Void> rollbackOrder(ReservationFailedInventoryCommand data) {
        log.info("Cancelling order for order id: {}", data.getOrderId().getValue());
        return orderApplicationService.findOrder(data.getOrderId())
                .flatMap(order -> {
                    order.cancel(data.getFailureMessages());
                    return orderRepository.update(order)
                            .doOnSuccess(__ ->
                                    log.info("Order with id: {} is cancelled", order.getId().getValue())
                            ).then();
                });
    }
}
