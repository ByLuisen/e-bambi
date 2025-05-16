package com.commerce.inventory_service.domain;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("products")
public class Product {
    @Id
    private UUID id;

    @NotNull
    @Column("brand_id")
    private UUID brandId;

    @NotNull
    @Column("department_id")
    private UUID departmentId;

    @NotNull
    @Column("product_status_id")
    private UUID productStatusId;

    @NotNull
    private String sku;

    @NotNull
    private String name;

    @NotNull
    private BigDecimal price;

    @NotNull
    private String description;

    @Transient
    @Column("created_at")
    private LocalDateTime createdAt;

    @Transient
    @Column("updated_at")
    private LocalDateTime updatedAt;

    @Transient
    @Column("deleted_at")
    private LocalDateTime deletedAt;

    @Version
    @EqualsAndHashCode.Exclude
    @Column("version_number")
    private Integer versionNumber;
}
