package com.e.bambi.payment.domain.entity;

import com.e.bambi.shared.kernel.domain.entity.AggregateRoot;
import com.e.bambi.shared.kernel.domain.valueobject.PaymentMethodId;

public class PaymentMethod extends AggregateRoot<PaymentMethodId> {

    private final String name;
    private final String description;

    private PaymentMethod(Builder builder) {
        super.setId(builder.paymentMethodId);
        name = builder.name;
        description = builder.description;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }


    public static final class Builder {
        private PaymentMethodId paymentMethodId;
        private String name;
        private String description;

        private Builder() {
        }

        public Builder paymentMethodId(PaymentMethodId val) {
            paymentMethodId = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder description(String val) {
            description = val;
            return this;
        }

        public PaymentMethod build() {
            return new PaymentMethod(this);
        }
    }
}
