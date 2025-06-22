package com.e.bambi.payment.application.paymentmethod.handler.command;

import com.e.bambi.payment.application.paymentmethod.dto.command.CreatePaymentMethodCommand;
import com.e.bambi.payment.application.paymentmethod.dto.response.PaymentMethodResponse;
import com.e.bambi.payment.application.paymentmethod.mapper.PaymentMethodApplicationMapper;
import com.e.bambi.payment.application.paymentmethod.port.outbound.repository.PaymentMethodRepository;
import com.e.bambi.payment.domain.paymentmethod.entity.PaymentMethod;
import com.e.bambi.shared.kernel.application.bus.CommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CreatePaymentMethodCommandHandler implements CommandHandler<Mono<PaymentMethodResponse>,
        CreatePaymentMethodCommand> {

    private final PaymentMethodRepository paymentMethodRepository;
    private final PaymentMethodApplicationMapper paymentMethodApplicationMapper;

    @Override
    @Transactional
    public Mono<PaymentMethodResponse> handle(CreatePaymentMethodCommand command) {
        PaymentMethod paymentMethod =
                paymentMethodApplicationMapper.createPaymentMethodCommandToPaymentMethod(command);
        paymentMethod.initializePaymentMethod();
        return paymentMethodRepository
                .insert(paymentMethod)
                .onErrorMap(DuplicateKeyException.class, e ->
                        new DuplicateKeyException("Payment method with name: " +
                                command.getName() + " could not be saved, please ensure the name is unique")
                )
                .map(paymentMethodApplicationMapper::toPaymentMethodResponse);
    }
}
