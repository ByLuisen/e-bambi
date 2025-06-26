package com.e.bambi.order.domain.order.valueobject;

import com.e.bambi.shared.kernel.domain.valueobject.SupplierId;
import lombok.Getter;

@Getter
public class OrderItemSupplier {
    private final SupplierId supplierId;
    private final String name;

    private OrderItemSupplier(Builder builder) {
        supplierId = builder.supplierId;
        name = builder.name;
    }

    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {
        private SupplierId supplierId;
        private String name;

        private Builder() {
        }

        public Builder supplierId(SupplierId val) {
            supplierId = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public OrderItemSupplier build() {
            return new OrderItemSupplier(this);
        }
    }
}
