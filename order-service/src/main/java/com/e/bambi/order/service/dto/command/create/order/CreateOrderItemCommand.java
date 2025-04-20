package com.e.bambi.order.service.dto.command.create.order;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UUID;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateOrderItemCommand {

    @UUID
    @NotNull
    private String productId;

    @NotNull
    @NotBlank
    private String sku;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String soldBy;

    @NotNull
    @Positive
    private Integer quantity;

    @NotNull
    @DecimalMin(value = "0.01", message = "The product price must be greater than zero")
    @Digits(integer = 10, fraction = 2, message = "The product price cannot have more than 2 decimal places")
    private BigDecimal price;

    @NotNull
    @DecimalMin(value = "0.01", message = "The total price must be greater than zero")
    @Digits(integer = 10, fraction = 2, message = "The total price cannot have more than 2 decimal places")
    private BigDecimal totalPrice;

    @NotNull
    @DecimalMin(value = "0.00", message = "The discount must be greater than zero")
    @Digits(integer = 10, fraction = 2, message = "The discount cannot have more than 2 decimal places")
    private BigDecimal discount;
}
