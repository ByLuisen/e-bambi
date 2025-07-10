package com.e.bambi.inventory.application.inventorymovement.mapper;

import com.e.bambi.inventory.application.inventorymovement.dto.command.CreateInventoryMovementCommand;
import com.e.bambi.inventory.application.inventorymovement.dto.response.InventoryMovementProductResponse;
import com.e.bambi.inventory.application.inventorymovement.dto.response.InventoryMovementResponse;
import com.e.bambi.inventory.domain.inventorymovement.entity.InventoryMovement;
import com.e.bambi.inventory.domain.inventorymovement.valueobject.InventoryMovementProduct;
import org.springframework.stereotype.Component;

@Component
public class InventoryMovementApplicationMapper {

    public InventoryMovement createInventoryMovementCommandToInventoryMovement(CreateInventoryMovementCommand command) {
        return InventoryMovement.builder()
                .supplierId(command.getSupplierId())
                .product(new InventoryMovementProduct(
                        command.getProduct().getId(),
                        command.getProduct().getSku(),
                        command.getProduct().getName()
                ))
                .movementTypeId(command.getMovementTypeId())
                .quantity(command.getQuantity())
                .build();
    }

    public InventoryMovementResponse toInventoryMovementResponse(InventoryMovement inventoryMovement) {
        return InventoryMovementResponse.builder()
                .id(inventoryMovement.getId().getValue())
                .supplierId(inventoryMovement.getSupplierId().getValue())
                .product(new InventoryMovementProductResponse(
                        inventoryMovement.getProduct().getId().getValue(),
                        inventoryMovement.getProduct().getSku(),
                        inventoryMovement.getProduct().getName()
                ))
                .movementTypeId(inventoryMovement.getMovementTypeId().getValue())
                .quantity(inventoryMovement.getQuantity())
                .previousStock(inventoryMovement.getPreviousStock())
                .newStock(inventoryMovement.getNewStock())
                .createdAt(inventoryMovement.getCreatedAt())
                .build();
    }

}
