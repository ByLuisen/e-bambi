package com.e.bambi.order.infrastructure.rest.order.dto.request.createorder;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UUID;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class CreateOrderRequestDto {

    @Valid
    @NotNull
    private CreateOrderPaymentMethodRequestDto paymentMethod;

    @Valid
    @NotNull
    private CreateOrderAddressRequestDto address;

    @Valid
    @NotNull
    private List<CreateOrderItemRequestDto> items;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false, message = "Total price must be greater than 0")
    private BigDecimal totalPrice;
}
