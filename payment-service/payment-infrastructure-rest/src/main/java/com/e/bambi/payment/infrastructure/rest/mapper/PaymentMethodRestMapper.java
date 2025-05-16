package com.e.bambi.payment.infrastructure.rest.mapper;

import com.e.bambi.payment.application.dto.command.CreatePaymentMethodCommand;
import com.e.bambi.payment.infrastructure.rest.dto.CreatePaymentMethodRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class PaymentMethodRestMapper {

    public CreatePaymentMethodCommand toCreatePaymentMethodCommand(CreatePaymentMethodRequestDTO
                                                                           createPaymentMethodRequestDTO) {
        return new CreatePaymentMethodCommand(
                createPaymentMethodRequestDTO.name(),
                createPaymentMethodRequestDTO.description()
        );
    }
}
