package com.e.bambi.inventory.infrastructure.persistence.inventorymovement.mapper;

import com.e.bambi.inventory.application.inventorymovement.dto.response.InventoryMovementSummaryProduct;
import com.e.bambi.inventory.application.inventorymovement.dto.response.InventoryMovementSummaryReadResponse;
import com.e.bambi.inventory.application.inventorymovement.dto.response.InventoryMovementSummarySupplier;
import com.e.bambi.inventory.domain.inventorymovement.entity.InventoryMovement;
import com.e.bambi.inventory.domain.inventorymovement.valueobject.InventoryMovementId;
import com.e.bambi.inventory.domain.shared.valueobject.MovementTypeId;
import com.e.bambi.shared.kernel.domain.valueobject.SupplierId;
import com.e.bambi.inventory.infrastructure.persistence.inventorymovement.entity.InventoryMovementEntity;
import com.e.bambi.shared.kernel.domain.valueobject.ProductId;
import org.jooq.Record;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class InventoryMovementPersistenceMapper {

    public InventoryMovementEntity toInventoryMovementEntity(InventoryMovement inventoryMovement) {
        return InventoryMovementEntity.builder()
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

    public InventoryMovement toInventoryMovement(InventoryMovementEntity entity) {
        return InventoryMovement.builder()
                .id(new InventoryMovementId(entity.getId()))
                .supplierId(new SupplierId(entity.getId()))
                .productId(new ProductId(entity.getProductId()))
                .movementTypeId(new MovementTypeId(entity.getMovementTypeId()))
                .productSku(entity.getProductSku())
                .productName(entity.getProductName())
                .quantity(entity.getQuantity())
                .previousStock(entity.getPreviousStock())
                .newStock(entity.getNewStock())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public InventoryMovementSummaryReadResponse toInventoryMovementSummaryReadResponse(Record r) {
        return InventoryMovementSummaryReadResponse.builder()
                .supplier(new InventoryMovementSummarySupplier(
                        r.get("supplier_id", UUID.class),
                        r.get("supplier_name", String.class)
                ))
                .product(new InventoryMovementSummaryProduct(
                        r.get("product_id", UUID.class),
                        r.get("product_sku", String.class),
                        r.get("product_name", String.class)
                ))
                .movementType(r.get("movement_type", String.class))
                .quantity(r.get("quantity", Integer.class))
                .previousStock(r.get("previous_stock", Integer.class))
                .newStock(r.get("new_stock", Integer.class))
                .createdAt(r.get("created_at", Instant.class))
                .build();
    }
}
