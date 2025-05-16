package com.e.bambi.order.application.handler.command;

import com.e.bambi.order.application.dto.command.CreateOrderCommand;
import com.e.bambi.order.application.dto.response.CreateOrderResponse;
import com.e.bambi.order.application.mapper.OrderApplicationMapper;
import com.e.bambi.order.application.port.outbound.repository.OrderItemRepository;
import com.e.bambi.order.application.port.outbound.repository.OrderRepository;
import com.e.bambi.order.domain.entity.Order;
import com.e.bambi.shared.kernel.domain.valueobject.OrderId;
import com.e.bambi.shared.kernel.application.bus.CommandHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateOrderCommandHandler implements CommandHandler<Mono<CreateOrderResponse>, CreateOrderCommand> {

    private final OrderApplicationMapper orderApplicationMapper;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    @Transactional
    public Mono<CreateOrderResponse> handle(CreateOrderCommand command) {
        return this.persistOrder(command)
                .map(savedOrder -> {
                    log.info("Returning CreateOrderResponse with order id: {}",
                            savedOrder.getId().getValue());

                    return orderApplicationMapper.toCreateOrderResponse(savedOrder,
                            "Order created successfully");
                });
    }

    private Mono<Order> persistOrder(CreateOrderCommand createOrderCommand) {
        return orderRepository.save(orderApplicationMapper.createOrderCommandToOrderDomain(createOrderCommand))
                .flatMap(savedOrder -> {
                    OrderId orderId = savedOrder.getId();
                    return orderItemRepository
                            .saveAll(orderApplicationMapper
                                    .createOrderItemCommandToOrderItem(
                                            createOrderCommand.getItems()), orderId)
                            .collectList()
                            .map(items -> {
                                        savedOrder.addItems(items);
                                return savedOrder;
                            });
                });
    }
}
