package com.e.bambi.inventory.application.inventorymovement.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;


@Getter
@Builder
public class InventoryMovementSummaryReadResponse {
    private final InventoryMovementSummarySupplier supplier;
    private final InventoryMovementSummaryProduct product;
    private final String movementType;
    private final Integer quantity;
    private final Integer previousStock;
    private final Integer newStock;
    private final Instant createdAt;
}
