package com.e.bambi.order.infrastructure.persistence.order.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("order_items")
public class OrderItemEntity {

    @Id
    private UUID id;
    @Column("order_id")
    private UUID orderId;
    @Column("product_id")
    private UUID productId;
    @Column("sku")
    private String sku;
    @Column("name")
    private String name;
    @Column("sold_by")
    private String soldBy;
    @Column("quantity")
    private Integer quantity;
    @Column("price")
    private BigDecimal price;
    @Column("total_price")
    private BigDecimal totalPrice;
    private BigDecimal discount;
    @Column("created_at")
    private ZonedDateTime createdAt;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderItemEntity orderItem = (OrderItemEntity) o;
        return Objects.equals(id, orderItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
