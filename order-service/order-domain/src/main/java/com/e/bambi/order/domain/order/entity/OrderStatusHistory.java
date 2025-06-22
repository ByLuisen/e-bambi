package com.e.bambi.order.domain.order.entity;

import com.e.bambi.shared.kernel.domain.valueobject.OrderId;
import com.e.bambi.order.domain.order.valueobject.OrderStatusHistoryId;
import com.e.bambi.shared.kernel.domain.entity.BaseEntity;
import com.e.bambi.order.domain.order.valueobject.OrderStatus;
import lombok.Getter;

import java.time.Instant;
import java.time.OffsetDateTime;

@Getter
public class OrderStatusHistory extends BaseEntity<OrderStatusHistoryId> {
    private final OrderId orderId;
    private final OrderStatus orderStatus;
    private final String reason;
    private final OffsetDateTime changedAt;

    private OrderStatusHistory(Builder builder) {
         super.setId(builder.id);
        orderId = builder.orderId;
        orderStatus = builder.orderStatus;
        reason = builder.reason;
        changedAt = builder.changedAt;
    }

    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {
        private OrderStatusHistoryId id;
        private OrderId orderId;
        private OrderStatus orderStatus;
        private String reason;
        private OffsetDateTime changedAt;

        private Builder() {
        }

        public Builder id(OrderStatusHistoryId val) {
            id = val;
            return this;
        }

        public Builder orderId(OrderId val) {
            orderId = val;
            return this;
        }

        public Builder orderStatus(OrderStatus val) {
            orderStatus = val;
            return this;
        }

        public Builder reason(String val) {
            reason = val;
            return this;
        }

        public Builder changedAt(OffsetDateTime val) {
            changedAt = val;
            return this;
        }

        public OrderStatusHistory build() {
            return new OrderStatusHistory(this);
        }
    }
}
