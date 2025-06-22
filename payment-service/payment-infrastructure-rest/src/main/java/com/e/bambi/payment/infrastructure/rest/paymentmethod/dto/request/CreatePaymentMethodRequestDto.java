package com.e.bambi.payment.infrastructure.rest.paymentmethod.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreatePaymentMethodRequestDto(
        @NotNull
        @NotBlank
        @Size(max = 50)
        String name,

        @NotNull
        @NotBlank
        @Size(max = 255)
        String description
) {
}