package com.e.bambi.order.application.order.handler.command.message.inventory;

import com.e.bambi.order.application.order.dto.command.message.inventory.ReservationFailedInventoryCommand;
import com.e.bambi.order.application.order.saga.OrderInventorySaga;
import com.e.bambi.shared.kernel.application.bus.CommandHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import static com.e.bambi.shared.kernel.application.saga.order.SagaConstants.FAILURE_MESSAGE_DELIMITER;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservationFailedInventoryCommandHandler implements
        CommandHandler<Mono<Void>, ReservationFailedInventoryCommand> {

    private final OrderInventorySaga orderInventorySaga;

    @Override
    @Transactional
    public Mono<Void> handle(ReservationFailedInventoryCommand command) {
        return orderInventorySaga.rollback(command)
                .doOnSuccess(__ -> log.info("Order is roll backed for order id: {} with failure messages: {}",
                        command.getOrderId().getValue(),
                        String.join(FAILURE_MESSAGE_DELIMITER, command.getFailureMessages())));
    }
}
