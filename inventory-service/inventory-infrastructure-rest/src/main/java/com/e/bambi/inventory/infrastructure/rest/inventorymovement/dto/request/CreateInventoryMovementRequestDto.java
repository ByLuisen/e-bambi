package com.e.bambi.inventory.infrastructure.rest.inventorymovement.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UUID;

@Getter
@Setter
public class CreateInventoryMovementRequestDto {

    @UUID
    @NotNull(message = "The product id cannot be null.")
    private String supplierId;

    @Valid
    private CreateInventoryMovementProduct product;

    @UUID
    @NotNull(message = "The movement type id cannot be null.")
    private String movementTypeId;

    @NotNull(message = "The quantity cannot be null.")
    @Min(value = -1000)
    @Max(value = 1000)
    private Integer quantity;
}
