package com.e.bambi.payment.application.paymentmethod.handler.command;

import com.e.bambi.payment.application.paymentmethod.dto.command.DeletePaymentMethodByIdCommand;
import com.e.bambi.payment.application.paymentmethod.port.outbound.repository.PaymentMethodRepository;
import com.e.bambi.payment.domain.exception.PaymentMethodNotFoundException;
import com.e.bambi.shared.kernel.application.bus.CommandHandler;
import com.e.bambi.shared.kernel.domain.valueobject.PaymentMethodId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class DeletePaymentMethodByIdCommandHandler implements CommandHandler<Mono<Void>, DeletePaymentMethodByIdCommand> {

    private final PaymentMethodRepository paymentMethodRepository;

    @Override
    public Mono<Void> handle(DeletePaymentMethodByIdCommand command) {
        PaymentMethodId paymentMethodId = command.getPaymentMethodId();

        return paymentMethodRepository.deleteById(paymentMethodId)
                .handle((updatedRows, sink) -> {
                    if (updatedRows < 1) {
                        sink.error(new PaymentMethodNotFoundException("Payment method with id: " +
                                paymentMethodId.getValue() + " could not be found"));
                    } else {
                        sink.complete();
                    }
                });
    }
}
