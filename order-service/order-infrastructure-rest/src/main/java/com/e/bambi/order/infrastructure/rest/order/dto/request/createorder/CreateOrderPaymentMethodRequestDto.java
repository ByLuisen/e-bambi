package com.e.bambi.order.infrastructure.rest.order.dto.request.createorder;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UUID;

@Getter
@Setter
public class CreateOrderPaymentMethodRequestDto {

    @UUID
    @NotNull
    private String id;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[\\p{L} ]+$", flags = Pattern.Flag.UNICODE_CASE)
    private String name;
}
