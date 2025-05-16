package com.e.bambi.order.domain.entity;

import com.e.bambi.shared.kernel.domain.valueobject.OrderId;
import com.e.bambi.order.domain.valueobject.OrderStatusHistoryId;
import com.e.bambi.shared.kernel.domain.entity.BaseEntity;
import lombok.Getter;

import java.time.Instant;

@Getter
public class OrderStatusHistory extends BaseEntity<OrderStatusHistoryId> {
    private final OrderId orderId;
    private final OrderStatus orderStatus;
    private final String reason;
    private final Instant changedAt;

    private OrderStatusHistory(Builder builder) {
        super.setId(builder.orderStatusHistoryId);
        orderId = builder.orderId;
        orderStatus = builder.orderStatus;
        reason = builder.reason;
        changedAt = builder.changedAt;
    }

    public static final class Builder {
        private OrderStatusHistoryId orderStatusHistoryId;
        private OrderId orderId;
        private OrderStatus orderStatus;
        private String reason;
        private Instant changedAt;

        private Builder() {
        }

        public Builder id(OrderStatusHistoryId val) {
            orderStatusHistoryId = val;
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

        public Builder changedAt(Instant val) {
            changedAt = val;
            return this;
        }

        public OrderStatusHistory build() {
            return new OrderStatusHistory(this);
        }
    }
}
