package com.e.bambi.inventory.service.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("inventory_movements")
public class InventoryMovement {
    @Id
    private UUID id;

    @NotNull
    @Column("product_id")
    private UUID productId;

    @NotNull
    @Column("movement_type_id")
    private UUID movementTypeId;

    @NotNull
    @Column("product_sku")
    private String productSku;

    @NotNull
    @Column("product_name")
    private String productName;

    @NotNull
    private Integer quantity;

    @NotNull
    @Column("previous_stock")
    private Integer previousStock;

    @NotNull
    @Column("new_stock")
    private Integer newStock;

    @NotNull
    @Column("reference_id")
    private UUID referenceId;

    @NotNull
    @Column("reference_table")
    private String referenceTable;

    private LocalDateTime created_at;
}
