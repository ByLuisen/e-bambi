package com.commerce.inventory_service.domain;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("product_supplier")
public class ProductSupplier {
    @Id
    private UUID id;

    @NotNull
    @Column("product_id")
    private UUID productId;

    @NotNull
    @Column("supplier_id")
    private UUID supplierId;

    @NotNull
    private BigDecimal price;

    @NotNull
    private Integer stock;
}
