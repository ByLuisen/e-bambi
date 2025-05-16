package com.commerce.inventory_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UUID;

@Setter
public class ProductReservationInputDTO {

    @UUID
    @NotNull
    private String productId;

    @UUID
    @NotNull
    private String supplierId;

    @Getter
    @PositiveOrZero
    @NotNull
    private Integer quantity;

    public java.util.UUID getProductId() {
        return java.util.UUID.fromString(this.productId);
    }

    public java.util.UUID getSupplierId() {
        return java.util.UUID.fromString(this.supplierId);
    }
}
