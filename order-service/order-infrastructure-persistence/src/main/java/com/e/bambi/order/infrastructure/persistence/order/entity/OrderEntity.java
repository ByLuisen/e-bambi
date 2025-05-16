package com.e.bambi.order.infrastructure.persistence.order.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("orders")
public class OrderEntity {

    @Id
    private UUID id;
    @Column("tracking_id")
    private UUID trackingId;
    @Column("status_id")
    private UUID statusId;
    @Column("payment_method_id")
    private UUID paymentMethodId;
    @Column("user_id")
    private UUID userId;
    @Column("total_price")
    private BigDecimal totalPrice;
    @Column("failure_messages")
    private String failureMessages;
    @Column("created_at")
    private ZonedDateTime createdAt;
    @Column("updated_at")
    private Instant updatedAt;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderEntity order = (OrderEntity) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
