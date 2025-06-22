package com.e.bambi.order.infrastructure.persistence.order.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("order_items")
public class OrderItemEntity {

    @Id
    private UUID id;
    @Column("order_id")
    private UUID orderId;
    @Column("image_url")
    private String imageUrl;
    @Column("supplier_id")
    private UUID supplierId;
    private String supplier;
    @Column("product_id")
    private UUID productId;
    private String sku;
    private String name;
    private BigDecimal price;
    private Integer quantity;
    @Column("total_price")
    private BigDecimal totalPrice;

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
