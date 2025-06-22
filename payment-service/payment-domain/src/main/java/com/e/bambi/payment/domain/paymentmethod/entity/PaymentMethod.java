package com.e.bambi.payment.domain.paymentmethod.entity;

import com.e.bambi.shared.kernel.domain.entity.AggregateRoot;
import com.e.bambi.shared.kernel.domain.valueobject.PaymentMethodId;
import lombok.Getter;

import java.util.UUID;

@Getter
public class PaymentMethod extends AggregateRoot<PaymentMethodId> {

    private final String name;
    private final String description;

    public void initializePaymentMethod() {
        super.setId(new PaymentMethodId(UUID.randomUUID()));
    }

    private PaymentMethod(Builder builder) {
        super.setId(builder.paymentMethodId);
        name = builder.name;
        description = builder.description;
    }

    public static Builder builder() {
        return new Builder();
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
