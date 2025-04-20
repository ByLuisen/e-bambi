package com.e.bambi.inventory.service.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record InventoryMovementOutputDTO(UUID id, MovementTypeOutputDTO movementType, UUID productId, String productSku,
                                         String productName, Integer quantity, Integer previousStock, Integer newStock,
                                         UUID referenceId, String referenceTable, LocalDateTime createdAt) {
}