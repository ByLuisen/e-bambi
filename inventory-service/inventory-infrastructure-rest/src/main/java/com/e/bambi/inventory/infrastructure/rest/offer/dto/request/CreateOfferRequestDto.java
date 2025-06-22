package com.e.bambi.inventory.infrastructure.rest.offer.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UUID;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateOfferRequestDto {
    @UUID
    @NotNull
    private String productId;
    @NotNull
    @DecimalMin(value = "0.01", message = "The price must be greater than zero")
    @Digits(integer = 5, fraction = 2, message = "The price cannot have more than 2 decimal places")
    private BigDecimal price;
    @NotNull
    @Positive(message = "The stock must be greater than zero")
    private int stock;
}
