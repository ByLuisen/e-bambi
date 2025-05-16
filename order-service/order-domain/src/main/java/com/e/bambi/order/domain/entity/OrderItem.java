package com.e.bambi.order.domain.entity;

import com.e.bambi.shared.kernel.domain.valueobject.Money;
import com.e.bambi.shared.kernel.domain.valueobject.OrderId;
import com.e.bambi.order.domain.valueobject.OrderItemId;
import com.e.bambi.shared.kernel.domain.entity.BaseEntity;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public class OrderItem extends BaseEntity<OrderItemId> {
    private final OrderId orderId;
    private final Product product;
    private final String soldBy;
    private final Integer quantity;
    private final Money totalPrice;
    private final Money discount;
    private final ZonedDateTime createdAt;

    private OrderItem(Builder builder) {
        super.setId(builder.orderItemId);
        orderId = builder.orderId;
        product = builder.product;
        soldBy = builder.soldBy;
        quantity = builder.quantity;
        totalPrice = builder.totalPrice;
        discount = builder.discount;
        createdAt = builder.createdAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private OrderItemId orderItemId;
        private OrderId orderId;
        private Product product;
        private String soldBy;
        private Integer quantity;
        private Money totalPrice;
        private Money discount;
        private ZonedDateTime createdAt;

        private Builder() {
        }

        public Builder orderItemId(OrderItemId val) {
            orderItemId = val;
            return this;
        }

        public Builder orderId(OrderId val) {
            orderId = val;
            return this;
        }

        public Builder product(Product val) {
            this.product = val;
            return this;
        }

        public Builder soldBy(String val) {
            soldBy = val;
            return this;
        }

        public Builder quantity(Integer val) {
            quantity = val;
            return this;
        }

        public Builder totalPrice(Money val) {
            totalPrice = val;
            return this;
        }

        public Builder discount(Money val) {
            discount = val;
            return this;
        }

        public Builder createdAt(ZonedDateTime val) {
            createdAt = val;
            return this;
        }

        public OrderItem build() {
            return new OrderItem(this);
        }
    }
}
