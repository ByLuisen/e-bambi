package com.e.bambi.payment.application.paymentmethod.handler.command;

import com.e.bambi.payment.application.paymentmethod.dto.command.UpdatePaymentMethodCommand;
import com.e.bambi.payment.application.paymentmethod.dto.response.PaymentMethodResponse;
import com.e.bambi.payment.application.paymentmethod.mapper.PaymentMethodApplicationMapper;
import com.e.bambi.payment.application.paymentmethod.port.outbound.repository.PaymentMethodRepository;
import com.e.bambi.payment.domain.exception.PaymentMethodNotFoundException;
import com.e.bambi.shared.kernel.application.bus.CommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UpdatePaymentMethodCommandHandler implements CommandHandler<Mono<PaymentMethodResponse>, UpdatePaymentMethodCommand> {

    private final PaymentMethodApplicationMapper paymentMethodApplicationMapper;
    private final PaymentMethodRepository paymentMethodRepository;

    @Override
    public Mono<PaymentMethodResponse> handle(UpdatePaymentMethodCommand command) {
        return paymentMethodRepository.update(paymentMethodApplicationMapper
                        .updatePaymentMethodCommmandToPaymentMethod(command))
                .switchIfEmpty(Mono.error(new PaymentMethodNotFoundException("Payment Method with id: " +
                        command.getPaymentMethodId().getValue() + " could not be found"))
                ).onErrorMap(DuplicateKeyException.class, e ->
                        new DuplicateKeyException("Payment Method with name: " + command.getName() +
                                " already exists, please provide an unique Payment Method name")
                ).map(paymentMethodApplicationMapper::toPaymentMethodResponse);
    }
}
