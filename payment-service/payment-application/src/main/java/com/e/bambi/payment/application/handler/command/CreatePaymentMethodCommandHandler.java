package com.e.bambi.payment.application.handler.command;

import com.e.bambi.payment.application.dto.command.CreatePaymentMethodCommand;
import com.e.bambi.payment.application.dto.response.PaymentMethodResponse;
import com.e.bambi.payment.application.mapper.PaymentMethodApplicationMapper;
import com.e.bambi.payment.application.port.outbound.repository.PaymentMethodRepository;
import com.e.bambi.shared.kernel.application.bus.CommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CreatePaymentMethodCommandHandler implements CommandHandler<Mono<PaymentMethodResponse>,
        CreatePaymentMethodCommand> {

    private final PaymentMethodRepository paymentMethodRepository;
    private final PaymentMethodApplicationMapper paymentMethodApplicationMapper;

    @Override
    public Mono<PaymentMethodResponse> handle(CreatePaymentMethodCommand command) {
        return paymentMethodRepository
                .save(paymentMethodApplicationMapper.toPaymentMethod(command))
                .onErrorMap(DuplicateKeyException.class, e ->
                        new DuplicateKeyException("Payment method with name: " +
                                command.name() + " could not be saved, please ensure the name is unique")
                )
                .map(paymentMethodApplicationMapper::toPaymentMethodResponse);
    }
}
