package com.e.bambi.order.application.order.handler.command.message.payment;

import com.e.bambi.order.application.order.dto.command.message.payment.ValidatedPaymentCommand;
import com.e.bambi.order.application.order.saga.OrderPaymentSaga;
import com.e.bambi.shared.kernel.application.bus.CommandHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class ValidatedPaymentCommandHandler implements CommandHandler<Mono<Void>, ValidatedPaymentCommand> {

    private final OrderPaymentSaga orderPaymentSaga;

    @Override
    public Mono<Void> handle(ValidatedPaymentCommand command) {
        return orderPaymentSaga.process(command)
                .doOnSuccess(__ -> log.info("Order with id: {} is created",
                        command.getOrderId().getValue()));
    }
}
