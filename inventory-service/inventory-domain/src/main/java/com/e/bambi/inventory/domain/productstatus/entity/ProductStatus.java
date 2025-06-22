package com.e.bambi.inventory.domain.productstatus.entity;

import com.e.bambi.inventory.domain.productstatus.valueobject.ProductStatusId;
import com.e.bambi.shared.kernel.domain.entity.AggregateRoot;
import lombok.Getter;

@Getter
public class ProductStatus extends AggregateRoot<ProductStatusId> {
    private final String name;

    private ProductStatus(Builder builder) {
        super.setId(builder.productStatusId);
        name = builder.name;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private ProductStatusId productStatusId;
        private String name;

        private Builder() {
        }

        public Builder productStatusId(ProductStatusId val) {
            productStatusId = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public ProductStatus build() {
            return new ProductStatus(this);
        }
    }
}
