package com.e.bambi.order.infrastructure.persistence.order.entity;

import com.e.bambi.order.domain.order.valueobject.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("orders")
public class OrderEntity {

    @Id
    private UUID id;
    @Column("user_id")
    private UUID userId;
    @Column("order_status")
    private OrderStatus orderStatus;
    @Column("payment_method_id")
    private UUID paymentMethodId;
    @Column("payment_method")
    private String paymentMethod;
    private String country;
    private String address;
    private String city;
    private String province;
    @Column("postal_code")
    private String postalCode;
    @Column("phone_number")
    private String phoneNumber;
    @Column("total_price")
    private BigDecimal totalPrice;
    @Column("failure_messages")
    private String failureMessages;
    @Column("created_at")
    private OffsetDateTime createdAt;

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
