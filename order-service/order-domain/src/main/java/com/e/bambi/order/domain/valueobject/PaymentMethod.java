package com.e.bambi.order.domain.valueobject;

import com.e.bambi.shared.kernel.domain.valueobject.PaymentMethodId;
import lombok.Getter;

@Getter
public class PaymentMethod {
    private final PaymentMethodId id;
    private String name;

    public PaymentMethod(PaymentMethodId id, String name) {
        this.id = id;
        this.name = name;
    }

    public PaymentMethod(PaymentMethodId id) {
        this.id = id;
    }
}
