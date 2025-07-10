package com.e.bambi.order.application.order.handler.command.message.inventory;

import com.e.bambi.order.application.order.dto.command.message.inventory.ReservedInventoryCommand;
import com.e.bambi.order.application.order.saga.OrderInventorySaga;
import com.e.bambi.shared.kernel.application.bus.CommandHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservedInventoryCommandHandler implements CommandHandler<Mono<Void>, ReservedInventoryCommand> {

    private final OrderInventorySaga orderInventorySaga;

    @Override
    public Mono<Void> handle(ReservedInventoryCommand command) {
        return orderInventorySaga.process(command)
                .doOnSuccess(__ -> log.info("Reservation products has been confirmed for order id: {}",
                        command.getOrderId().getValue()));
    }
}
