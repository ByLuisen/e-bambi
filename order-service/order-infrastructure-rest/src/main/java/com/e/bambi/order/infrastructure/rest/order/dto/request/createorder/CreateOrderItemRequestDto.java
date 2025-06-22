package com.e.bambi.order.infrastructure.rest.order.dto.request.createorder;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateOrderItemRequestDto {

    @NotNull
    @NotBlank
    private String imageUrl;

    @Valid
    private CreateOrderItemSupplierRequestDto supplier;

    @Valid
    private CreateOrderItemProductRequestDto product;

    @NotNull
    @DecimalMin(value = "0.01", message = "The product price must be greater than zero")
    @Digits(integer = 10, fraction = 2, message = "The product price cannot have more than 2 decimal places")
    private BigDecimal price;

    @NotNull
    @Positive
    private Integer quantity;

    @NotNull
    @DecimalMin(value = "0.01", message = "The total price must be greater than zero")
    @Digits(integer = 10, fraction = 2, message = "The total price cannot have more than 2 decimal places")
    private BigDecimal totalPrice;
}