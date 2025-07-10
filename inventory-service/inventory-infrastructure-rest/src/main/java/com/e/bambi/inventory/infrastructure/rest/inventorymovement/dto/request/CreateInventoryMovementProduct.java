package com.e.bambi.inventory.infrastructure.rest.inventorymovement.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UUID;

@Setter
@Getter
public class CreateInventoryMovementProduct {

    @UUID
    @NotNull(message = "The product id cannot be null.")
    private String id;

    @NotNull(message = "The product SKU cannot be null.")
    @Pattern(regexp = "^[A-Za-z0-9_-]+$",
            message = "The product SKU can only contain letters, numbers, hyphens, and underscores.")
    private String sku;

    @NotBlank(message = "The product name cannot be blank.")
    @NotNull(message = "The product name cannot be null.")
    @Pattern(regexp = "^[A-Za-z0-9\\s\\-_,.]+$",
            message = "The product name can only contain letters, numbers, spaces, and basic punctuation marks.")
    private String name;
}
