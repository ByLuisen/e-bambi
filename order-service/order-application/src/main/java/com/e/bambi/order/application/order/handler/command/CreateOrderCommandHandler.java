package com.e.bambi.order.application.order.handler.command;

import com.e.bambi.order.application.order.OrderApplicationService;
import com.e.bambi.order.application.order.dto.command.createorder.CreateOrderCommand;
import com.e.bambi.order.application.order.dto.response.CreateOrderResponse;
import com.e.bambi.order.application.order.mapper.OrderApplicationMapper;
import com.e.bambi.order.application.order.port.outbound.repository.OrderRepository;
import com.e.bambi.order.application.outbox.OrderOutboxEventHelper;
import com.e.bambi.order.domain.event.OrderAggregateType;
import com.e.bambi.order.domain.OrderDomainService;
import com.e.bambi.order.domain.event.OrderInventoryReserveEvent;
import com.e.bambi.order.domain.exception.OrderDomainException;
import com.e.bambi.order.domain.order.entity.Order;
import com.e.bambi.shared.kernel.application.bus.CommandHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static com.e.bambi.shared.kernel.application.saga.order.SagaConstants.ORDER_SAGA_NAME;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateOrderCommandHandler implements CommandHandler<Mono<CreateOrderResponse>, CreateOrderCommand> {

    private final OrderDomainService orderDomainService;
    private final OrderApplicationMapper orderApplicationMapper;
    private final OrderOutboxEventHelper orderOutboxEventHelper;
    private final OrderApplicationService orderApplicationService;
    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public Mono<CreateOrderResponse> handle(CreateOrderCommand command) {
        return persistOrder(command)
                .map(savedOrder -> {
                    log.info("Returning CreateOrderResponse with order id: {}",
                            savedOrder.getId().getValue());

                    return orderApplicationMapper.toCreateOrderResponse(savedOrder,
                            "Order created successfully");
                });
    }

    private Mono<Order> persistOrder(CreateOrderCommand command) {
        Order order = orderApplicationMapper.createOrderCommandToOrderDomain(command);
        OrderInventoryReserveEvent event =
                orderDomainService.validateAndInitiateOrder(OrderAggregateType.INVENTORY_RESERVE.getValue(), order);

        return orderRepository.insert(order)
                .switchIfEmpty(Mono.error(new OrderDomainException("Could not save order!")))
                .flatMap(savedOrder -> {
                    log.info("Order is saved with id: {}", savedOrder.getId().getValue());
                    return orderOutboxEventHelper.saveOrderOutboxEvent(
                            event.getAggregatetype(),
                            event.toPayload(),
                            UUID.randomUUID().toString(),
                            ORDER_SAGA_NAME,
                            orderApplicationService.orderStatusToSagaStatus(order.getOrderStatus())
                    ).thenReturn(savedOrder);
                });

    }
}
