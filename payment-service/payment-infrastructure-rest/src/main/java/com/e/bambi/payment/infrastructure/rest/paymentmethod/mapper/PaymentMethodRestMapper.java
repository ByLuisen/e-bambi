package com.e.bambi.payment.infrastructure.rest.paymentmethod.mapper;

import com.e.bambi.payment.application.paymentmethod.dto.command.CreatePaymentMethodCommand;
import com.e.bambi.payment.application.paymentmethod.dto.command.UpdatePaymentMethodCommand;
import com.e.bambi.payment.infrastructure.rest.paymentmethod.dto.request.CreatePaymentMethodRequestDto;
import com.e.bambi.payment.infrastructure.rest.paymentmethod.dto.request.UpdatePaymentMethodRequestDto;
import com.e.bambi.shared.kernel.domain.valueobject.PaymentMethodId;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PaymentMethodRestMapper {

    public CreatePaymentMethodCommand toCreatePaymentMethodCommand(CreatePaymentMethodRequestDto
                                                                           createPaymentMethodRequestDTO) {
        return new CreatePaymentMethodCommand(
                createPaymentMethodRequestDTO.name(),
                createPaymentMethodRequestDTO.description()
        );
    }

    public UpdatePaymentMethodCommand toUpdatePaymentMethodCommand(String paymentMethodId,
                                                                   UpdatePaymentMethodRequestDto request) {
        return new UpdatePaymentMethodCommand(
                new PaymentMethodId(UUID.fromString(paymentMethodId)),
                request.name(),
                request.description()
        );
    }
}
