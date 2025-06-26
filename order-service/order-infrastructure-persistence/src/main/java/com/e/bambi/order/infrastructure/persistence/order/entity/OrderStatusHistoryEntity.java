package com.e.bambi.order.infrastructure.persistence.order.entity;

import com.e.bambi.order.domain.order.valueobject.OrderStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("order_status_history")
public class OrderStatusHistoryEntity {

    @Id
    private UUID id;
    @Column("order_id")
    private UUID orderId;
    @Column("order_status")
    private OrderStatus orderStatus;
    private String reason;
    @Column("created_at")
    private OffsetDateTime createdAt;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderStatusHistoryEntity that = (OrderStatusHistoryEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
