package com.e.bambi.inventory.infrastructure.persistence.inventorymovement.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("inventory_movements")
public class InventoryMovementEntity {

    @Id
    private UUID id;
    @Column("supplier_id")
    private UUID supplierId;
    @Column("product_id")
    private UUID productId;
    @Column("movement_type_id")
    private UUID movementTypeId;
    @Column("product_sku")
    private String productSku;
    @Column("product_name")
    private String productName;
    private Integer quantity;
    @Column("previous_stock")
    private Integer previousStock;
    @Column("new_stock")
    private Integer newStock;
    @Column("created_at")
    private OffsetDateTime createdAt;
}
