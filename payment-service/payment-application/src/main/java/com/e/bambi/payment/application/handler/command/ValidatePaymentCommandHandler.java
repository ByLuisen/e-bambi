package com.e.bambi.payment.application.handler.command;

import com.e.bambi.payment.application.PaymentApplicationService;
import com.e.bambi.payment.application.dto.command.ValidatePaymentCommand;
import com.e.bambi.shared.kernel.application.bus.CommandHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class ValidatePaymentCommandHandler implements CommandHandler<Mono<Void>, ValidatePaymentCommand> {

    private final PaymentApplicationService paymentApplicationService;

    @Override
    public Mono<Void> handle(ValidatePaymentCommand command) {
        return paymentApplicationService.validatePaymentMethod(command);
    }
}
