package com.e.bambi.payment.application.paymentmethod.mapper;

import com.e.bambi.payment.application.paymentmethod.dto.command.CreatePaymentMethodCommand;
import com.e.bambi.payment.application.paymentmethod.dto.command.UpdatePaymentMethodCommand;
import com.e.bambi.payment.application.paymentmethod.dto.response.PaymentMethodResponse;
import com.e.bambi.payment.domain.paymentmethod.entity.PaymentMethod;
import org.springframework.stereotype.Component;

@Component
public class PaymentMethodApplicationMapper {

    public PaymentMethodResponse toPaymentMethodResponse(PaymentMethod paymentMethods) {
        return new PaymentMethodResponse(
                paymentMethods.getId().getValue(),
                paymentMethods.getName(),
                paymentMethods.getDescription());
    }

    public PaymentMethod createPaymentMethodCommandToPaymentMethod(CreatePaymentMethodCommand createPaymentMethodCommand) {
        return PaymentMethod.builder()
                .name(createPaymentMethodCommand.getName())
                .description(createPaymentMethodCommand.getDescription())
                .build();
    }

    public PaymentMethod updatePaymentMethodCommmandToPaymentMethod(UpdatePaymentMethodCommand updatePaymentMethodCommand) {
        return PaymentMethod.builder()
                .paymentMethodId(updatePaymentMethodCommand.getPaymentMethodId())
                .name(updatePaymentMethodCommand.getName())
                .description(updatePaymentMethodCommand.getDescription())
                .build();
    }
}
