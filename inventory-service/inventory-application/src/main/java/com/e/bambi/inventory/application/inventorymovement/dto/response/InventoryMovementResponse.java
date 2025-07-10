package com.e.bambi.inventory.application.inventorymovement.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Builder
public class InventoryMovementResponse {
    private final UUID id;
    private final UUID supplierId;
    private final InventoryMovementProductResponse product;
    private final UUID movementTypeId;
    private final Integer quantity;
    private final Integer previousStock;
    private final Integer newStock;
    private final OffsetDateTime createdAt;
}