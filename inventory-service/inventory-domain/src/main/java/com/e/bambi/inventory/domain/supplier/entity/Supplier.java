package com.e.bambi.inventory.domain.supplier.entity;

import com.e.bambi.shared.kernel.domain.valueobject.SupplierId;
import com.e.bambi.shared.kernel.domain.entity.AggregateRoot;
import lombok.Getter;

@Getter
public class Supplier extends AggregateRoot<SupplierId> {
    private final String name;

    private Supplier(Builder builder) {
        super.setId(builder.id);
        name = builder.name;
    }

    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {
        private SupplierId id;
        private String name;

        private Builder() {
        }

        public Builder id(SupplierId val) {
            id = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Supplier build() {
            return new Supplier(this);
        }
    }
}
