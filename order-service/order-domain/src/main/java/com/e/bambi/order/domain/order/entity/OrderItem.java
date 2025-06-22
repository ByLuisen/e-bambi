package com.e.bambi.order.domain.order.entity;

import com.e.bambi.order.domain.order.valueobject.OrderItemId;
import com.e.bambi.order.domain.order.valueobject.OrderItemProduct;
import com.e.bambi.order.domain.order.valueobject.OrderItemSupplier;
import com.e.bambi.shared.kernel.domain.entity.BaseEntity;
import com.e.bambi.shared.kernel.domain.valueobject.Money;
import com.e.bambi.shared.kernel.domain.valueobject.OrderId;
import lombok.Getter;

@Getter
public class OrderItem extends BaseEntity<OrderItemId> {
    private OrderId orderId;
    private final String imageUrl;
    private final OrderItemSupplier supplier;
    private final OrderItemProduct product;
    private final Money price;
    private final int quantity;
    private final Money totalPrice;

    void initializeOrderItem(OrderItemId orderItemId, OrderId orderId) {
        super.setId(orderItemId);
        this.orderId = orderId;
    }

    boolean isPriceValid() {
        return price.isGreaterThanZero() &&
                price.multiply(quantity).equals(totalPrice);
    }

    private OrderItem(Builder builder) {
        super.setId(builder.id);
        orderId = builder.orderId;
        imageUrl = builder.imageUrl;
        supplier = builder.supplier;
        product = builder.product;
        price = builder.price;
        quantity = builder.quantity;
        totalPrice = builder.totalPrice;
    }

    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {
        private OrderItemId id;
        private OrderId orderId;
        private String imageUrl;
        private OrderItemSupplier supplier;
        private OrderItemProduct product;
        private Money price;
        private int quantity;
        private Money totalPrice;

        private Builder() {
        }

        public Builder id(OrderItemId val) {
            id = val;
            return this;
        }

        public Builder orderId(OrderId val) {
            orderId = val;
            return this;
        }

        public Builder imageUrl(String val) {
            imageUrl = val;
            return this;
        }

        public Builder supplier(OrderItemSupplier val) {
            supplier = val;
            return this;
        }

        public Builder product(OrderItemProduct val) {
            product = val;
            return this;
        }

        public Builder price(Money val) {
            price = val;
            return this;
        }

        public Builder quantity(int val) {
            quantity = val;
            return this;
        }

        public Builder totalPrice(Money val) {
            totalPrice = val;
            return this;
        }

        public OrderItem build() {
            return new OrderItem(this);
        }
    }
}
