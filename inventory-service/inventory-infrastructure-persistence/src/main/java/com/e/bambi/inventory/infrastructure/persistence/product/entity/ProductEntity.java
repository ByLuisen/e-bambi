package com.e.bambi.inventory.infrastructure.persistence.product.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("products")
public class ProductEntity {

    @Id
    private UUID id;
    @Column("brand_id")
    private UUID brandId;
    @Column("department_id")
    private UUID departmentId;
    @Column("product_status_id")
    private UUID productStatusId;
    private String sku;
    private String name;
    private String description;
    @Column("created_at")
    private Instant createdAt;
    @Column("updated_at")
    private Instant updatedAt;
}
