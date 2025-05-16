package com.e.bambi.payment.application.mapper;

import com.e.bambi.payment.application.dto.command.CreatePaymentMethodCommand;
import com.e.bambi.payment.application.dto.response.PaymentMethodResponse;
import com.e.bambi.payment.domain.entity.PaymentMethod;
import org.springframework.stereotype.Component;

@Component
public class PaymentMethodApplicationMapper {

    public PaymentMethodResponse toPaymentMethodResponse(PaymentMethod paymentMethods) {
        return new PaymentMethodResponse(
                paymentMethods.getId().getValue(),
                paymentMethods.getName(),
                paymentMethods.getDescription());
    }

    public PaymentMethod toPaymentMethod(CreatePaymentMethodCommand createPaymentMethodCommand) {
        return PaymentMethod.builder()
                .name(createPaymentMethodCommand.name())
                .description(createPaymentMethodCommand.description())
                .build();
    }
}
