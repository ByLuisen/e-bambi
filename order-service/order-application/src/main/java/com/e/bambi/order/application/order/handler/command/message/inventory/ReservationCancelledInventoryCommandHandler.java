package com.e.bambi.order.application.order.handler.command.message.inventory;

import com.e.bambi.order.application.order.OrderApplicationService;
import com.e.bambi.order.application.order.dto.command.message.inventory.ReservationCancelledInventoryCommand;
import com.e.bambi.order.application.order.port.outbound.repository.OrderRepository;
import com.e.bambi.order.application.order.port.outbound.repository.OrderStatusHistoryQueryRepository;
import com.e.bambi.order.domain.exception.OrderNotFoundException;
import com.e.bambi.order.domain.order.valueobject.OrderStatus;
import com.e.bambi.shared.kernel.application.bus.CommandHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservationCancelledInventoryCommandHandler implements
        CommandHandler<Mono<Void>, ReservationCancelledInventoryCommand> {

    private final OrderRepository orderRepository;
    private final OrderApplicationService orderApplicationService;
    private final OrderStatusHistoryQueryRepository orderStatusHistoryQueryRepository;

    @Override
    @Transactional
    public Mono<Void> handle(ReservationCancelledInventoryCommand command) {
        return orderStatusHistoryQueryRepository
                .existsByOrderIdAndOrderStatus(command.getOrderId(), OrderStatus.CANCELLED)
                .flatMap(exists -> {

                    if (exists) {
                        log.info("An order with id: {} is already cancelled", command.getOrderId().getValue());
                        return Mono.empty();
                    }

                    log.info("Cancelling order for order id: {}", command.getOrderId().getValue());
                    return orderApplicationService.findOrder(command.getOrderId())
                            .flatMap(order -> {
                                order.cancel(null);
                                return orderRepository.update(order)
                                        .doOnSuccess(__ -> log.info("Order with id: {} is cancelled",
                                                command.getOrderId().getValue()))
                                        .then();
                            })
                            .switchIfEmpty(Mono.error(new OrderNotFoundException("Order with id: " +
                                    command.getOrderId().getValue() + " could not be found")));
                });
    }
}
