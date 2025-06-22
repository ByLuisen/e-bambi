package com.e.bambi.inventory.application.inventorymovement.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Builder
public class InventoryMovementResponse {
    private final UUID id;
    private final UUID supplierId;
    private final UUID productId;
    private final UUID movementTypeId;
    private final String productSku;
    private final String productName;
    private final Integer quantity;
    private final Integer previousStock;
    private final Integer newStock;
    private final Instant createdAt;
}