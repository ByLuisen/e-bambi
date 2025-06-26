package com.e.bambi.order.domain.order.valueobject;

import com.e.bambi.shared.kernel.domain.valueobject.ProductId;
import lombok.Getter;

@Getter
public class OrderItemProduct {
    private final ProductId productId;
    private final String sku;
    private final String name;

    private OrderItemProduct(Builder builder) {
        productId = builder.productId;
        sku = builder.sku;
        name = builder.name;
    }

    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {
        private ProductId productId;
        private String sku;
        private String name;

        private Builder() {
        }

        public Builder productId(ProductId val) {
            productId = val;
            return this;
        }

        public Builder sku(String val) {
            sku = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public OrderItemProduct build() {
            return new OrderItemProduct(this);
        }
    }
}
