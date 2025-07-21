package com.e.bambi.inventory.infrastructure.persistence.offer.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("offers")
public class OfferEntity {

    @Id
    private UUID id;
    @Column("supplier_id")
    private UUID supplierId;
    @Column("product_id")
    private UUID productId;
    private BigDecimal price;
    private Integer stock;
}
