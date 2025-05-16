package com.e.bambi.order.domain.entity;

import com.e.bambi.order.domain.valueobject.*;
import com.e.bambi.shared.kernel.domain.entity.AggregateRoot;
import com.e.bambi.order.domain.valueobject.PaymentMethod;
import com.e.bambi.shared.kernel.domain.valueobject.Money;
import com.e.bambi.shared.kernel.domain.valueobject.OrderId;
import com.e.bambi.shared.kernel.domain.valueobject.UserId;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
public class Order extends AggregateRoot<OrderId> {
    private TrackingId trackingId;
    private final UserId userId;
    private final OrderStatus orderStatus;
    private final PaymentMethod paymentMethod;
    private final Money totalPrice;
    private final OrderAddress address;
    private List<OrderItem> items;
    private final ZonedDateTime createdAt;
    private final ZonedDateTime updatedAt;
    private List<String> failureMessages;

    public static final String FAILURE_MESSAGES_DELIMITER = ",";

    public void addItems(List<OrderItem> items) {
        this.items = items;
    }

    private Order(Builder builder) {
        super.setId(builder.orderId);
        userId = builder.userId;
        orderStatus = builder.orderStatus;
        paymentMethod = builder.paymentMethod;
        address = builder.address;
        items = builder.items;
        totalPrice = builder.totalPrice;
        createdAt = builder.createdAt;
        updatedAt = builder.updatedAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private OrderId orderId;
        private OrderStatus orderStatus;
        private PaymentMethod paymentMethod;
        private UserId userId;
        private OrderAddress address;
        private List<OrderItem> items;
        private Money totalPrice;
        private ZonedDateTime createdAt;
        private ZonedDateTime updatedAt;

        private Builder() {
        }

        public Builder id(OrderId val) {
            orderId = val;
            return this;
        }

        public Builder orderStatus(OrderStatus val) {
            orderStatus = val;
            return this;
        }

        public Builder paymentMethod(PaymentMethod val) {
            paymentMethod = val;
            return this;
        }

        public Builder userId(UserId val) {
            userId = val;
            return this;
        }

        public Builder address(OrderAddress val) {
            address = val;
            return this;
        }

        public Builder items(List<OrderItem> val) {
            items = val;
            return this;
        }

        public Builder totalPrice(Money val) {
            totalPrice = val;
            return this;
        }

        public Builder createdAt(ZonedDateTime val) {
            createdAt = val;
            return this;
        }

        public Builder updatedAt(ZonedDateTime val) {
            updatedAt = val;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
