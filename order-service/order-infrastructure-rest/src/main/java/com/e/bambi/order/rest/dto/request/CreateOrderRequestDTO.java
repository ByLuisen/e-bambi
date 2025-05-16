package com.e.bambi.order.rest.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.validator.constraints.UUID;

import java.math.BigDecimal;
import java.util.List;

@Getter
public class CreateOrderRequestDTO {

    @UUID
    @NotNull
    private String paymentMethodId;

    @Valid
    @NotNull
    private CreateOrderAddressRequestDTO address;

    @Valid
    @NotNull
    private List<CreateOrderItemRequestDTO> items;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false, message = "Total price must be greater than 0")
    private BigDecimal totalPrice;
}
