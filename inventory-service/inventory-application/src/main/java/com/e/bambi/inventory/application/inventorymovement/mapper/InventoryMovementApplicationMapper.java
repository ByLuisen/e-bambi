package com.e.bambi.inventory.application.inventorymovement.mapper;

import com.e.bambi.inventory.application.inventorymovement.dto.command.CreateInventoryMovementCommand;
import com.e.bambi.inventory.application.inventorymovement.dto.response.InventoryMovementResponse;
import com.e.bambi.inventory.domain.inventorymovement.entity.InventoryMovement;
import org.springframework.stereotype.Component;

@Component
public class InventoryMovementApplicationMapper {

    public InventoryMovement createInventoryMovementCommandToInventoryMovement(CreateInventoryMovementCommand command) {
        return InventoryMovement.builder()
                .productId(command.getProductId())
                .supplierId(command.getSupplierId())
                .movementTypeId(command.getMovementTypeId())
                .productSku(command.getProductSku())
                .productName(command.getProductName())
                .quantity(command.getQuantity())
                .build();
    }

    public InventoryMovementResponse toInventoryMovementResponse(InventoryMovement inventoryMovement) {
        return InventoryMovementResponse.builder()
                .id(inventoryMovement.getId().getValue())
                .supplierId(inventoryMovement.getSupplierId().getValue())
                .productId(inventoryMovement.getProductId().getValue())
                .movementTypeId(inventoryMovement.getMovementTypeId().getValue())
                .productSku(inventoryMovement.getProductSku())
                .productName(inventoryMovement.getProductName())
                .quantity(inventoryMovement.getQuantity())
                .previousStock(inventoryMovement.getPreviousStock())
                .newStock(inventoryMovement.getNewStock())
                .createdAt(inventoryMovement.getCreatedAt())
                .build();
    }

}
