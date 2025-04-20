package com.e.bambi.inventory.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UUID;

@Getter
@Setter
public class InventoryMovementInputDTO {

    @UUID
    @NotNull(message = "The product id cannot be null.")
    private String productId;

    @UUID
    @NotNull(message = "The movement type id cannot be null.")
    private String movementTypeId;

    @NotNull(message = "The product SKU cannot be null.")
    @Pattern(regexp = "^[A-Za-z0-9_-]+$", message = "The product SKU can only contain letters, numbers, hyphens, and underscores.")
    private String productSku;

    @NotBlank(message = "The product name cannot be blank.")
    @NotNull(message = "The product name cannot be null.")
    @Pattern(regexp = "^[A-Za-z0-9\\s\\-_,.]+$", message = "The product name can only contain letters, numbers, spaces, and basic punctuation marks.")
    private String productName;

    @NotNull(message = "The quantity cannot be null.")
    @Positive(message = "The amount must be a positive integer")
    private Integer quantity;

    @UUID
    @NotNull(message = "The reference id cannot be null.")
    private String referenceId;

    @NotNull(message = "The reference table cannot be null.")
    @Pattern(regexp = "^[A-Za-z0-9_]+$", message = "The reference table name can only contain letters, numbers, and underscores.")
    private String referenceTable;
}
