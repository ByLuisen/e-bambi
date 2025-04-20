package com.e.bambi.order.service.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("order_items")
public class OrderItem {

    @Id
    private UUID id;

    @NotNull
    @Column("order_id")
    private UUID orderId;

    @NotNull
    @Column("product_id")
    private UUID productId;

    @NotNull
    @Column("sku")
    private String sku;

    @NotNull
    @Column("name")
    private String name;

    @NotNull
    @Column("sold_by")
    private String soldBy;

    @NotNull
    @Column("quantity")
    private Integer quantity;

    @NotNull
    @Column("price")
    private BigDecimal price;

    @NotNull
    @Column("total_price")
    private BigDecimal totalPrice;

    private BigDecimal discount;

    @Column("created_at")
    private LocalDateTime createdAt;
}
