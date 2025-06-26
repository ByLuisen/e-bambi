package com.e.bambi.order.application.order.saga;

import com.e.bambi.order.application.order.OrderApplicationService;
import com.e.bambi.order.application.order.dto.command.message.payment.ValidatedPaymentCommand;
import com.e.bambi.order.application.order.dto.command.message.payment.ValidationFailedPaymentCommand;
import com.e.bambi.order.application.order.port.outbound.repository.OrderRepository;
import com.e.bambi.order.application.order.port.outbound.repository.OrderStatusHistoryQueryRepository;
import com.e.bambi.order.application.outbox.OrderOutboxEventHelper;
import com.e.bambi.order.domain.event.OrderAggregateType;
import com.e.bambi.order.domain.OrderDomainService;
import com.e.bambi.order.domain.event.OrderInventoryCancelReservationEvent;
import com.e.bambi.order.domain.order.valueobject.OrderStatus;
import com.e.bambi.shared.kernel.application.saga.SagaStatus;
import com.e.bambi.shared.kernel.application.saga.SagaStep;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import static com.e.bambi.shared.kernel.application.saga.order.SagaConstants.ORDER_SAGA_NAME;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderPaymentSaga implements SagaStep<ValidatedPaymentCommand, ValidationFailedPaymentCommand> {

    private final OrderRepository orderRepository;
    private final OrderApplicationService orderApplicationService;
    private final OrderOutboxEventHelper orderOutboxEventHelper;
    private final OrderDomainService orderDomainService;
    private final OrderStatusHistoryQueryRepository orderStatusHistoryQueryRepository;

    @Override
    public Mono<Void> process(ValidatedPaymentCommand data) {
        return orderStatusHistoryQueryRepository
                .existsByOrderIdAndOrderStatus(data.getOrderId(), OrderStatus.CREATED)
                .flatMap(exists -> {
                    if (exists) {
                        log.info("An order with order id: {} and order status: {} is already created",
                                data.getOrderId().getValue(), OrderStatus.CREATED);
                        return Mono.empty();
                    }

                    return createOrder(data);
                });
    }

    @Override
    public Mono<Void> rollback(ValidationFailedPaymentCommand data) {
        return orderStatusHistoryQueryRepository
                .existsByOrderIdAndOrderStatus(data.getOrderId(), OrderStatus.CANCELLING)
                .flatMap(exists -> {
                    if (exists) {
                        log.info("An order with id: {} and order status: {} is already roll back!",
                                data.getSagaId(), OrderStatus.CANCELLING);
                        return Mono.empty();
                    }

                    return rollbackOrder(data)
                            .flatMap(event -> {
                                SagaStatus sagaStatus = orderApplicationService
                                        .orderStatusToSagaStatus(event.getOrder().getOrderStatus());

                                return orderOutboxEventHelper
                                        .saveOrderOutboxEvent(
                                                event.getAggregatetype(),
                                                event.toPayload(),
                                                data.getSagaId(),
                                                ORDER_SAGA_NAME,
                                                sagaStatus
                                        ).flatMap(orderOutboxEventId -> {
                                            log.info("Order with id: {} is cancelling",
                                                    event.getOrder().getId().getValue());

                                            return orderOutboxEventHelper.deleteOrderOutboxEvent(orderOutboxEventId);
                                        });
                            });
                });
    }

    private Mono<Void> createOrder(ValidatedPaymentCommand data) {
        log.info("Creating order for order id: {}", data.getOrderId().getValue());
        return orderApplicationService.findOrder(data.getOrderId())
                .flatMap(order -> {
                    order.created();

                    return orderRepository.update(order)
                            .doOnSuccess(__ ->
                                    log.info("Order with id: {} is created", order.getId().getValue()))
                            .then();
                });
    }

    private Mono<OrderInventoryCancelReservationEvent> rollbackOrder(ValidationFailedPaymentCommand data) {
        log.info("Cancelling order with id: {}", data.getOrderId().getValue());
        return orderApplicationService.findOrder(data.getOrderId())
                .flatMap(order -> {
                    OrderInventoryCancelReservationEvent event =
                            orderDomainService.cancelOrderInventory(
                                    OrderAggregateType.INVENTORY_CANCEL_RESERVATION.getValue(),
                                    order, data.getFailureMessages());

                    return orderRepository.update(order)
                            .thenReturn(event);
                });

    }
}
