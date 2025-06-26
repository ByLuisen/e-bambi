package com.e.bambi.order.application.order.handler.command.message.payment;

import com.e.bambi.order.application.order.dto.command.message.payment.ValidationFailedPaymentCommand;
import com.e.bambi.order.application.order.saga.OrderPaymentSaga;
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
public class ValidationFailedPaymentCommandHandler implements CommandHandler<Mono<Void>, ValidationFailedPaymentCommand> {

    private final OrderPaymentSaga orderPaymentSaga;

    @Override
    @Transactional
    public Mono<Void> handle(ValidationFailedPaymentCommand command) {
        return orderPaymentSaga.rollback(command)
                .doOnSuccess(__ ->
                        log.info("Order Payment Saga rollback operation is completed for order id: {} with failure " +
                                        "messages: {}",
                                command.getOrderId().getValue(),
                                String.join(FAILURE_MESSAGE_DELIMITER, command.getFailureMessages()))
                );
    }
}
