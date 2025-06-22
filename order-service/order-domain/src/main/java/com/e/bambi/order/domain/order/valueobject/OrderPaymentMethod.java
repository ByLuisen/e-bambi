package com.e.bambi.order.domain.order.valueobject;

import com.e.bambi.shared.kernel.domain.valueobject.PaymentMethodId;
import lombok.Getter;

@Getter
public class OrderPaymentMethod {
    private final PaymentMethodId id;
    private String name;

    public OrderPaymentMethod(PaymentMethodId id, String name) {
        this.id = id;
        this.name = name;
    }

    public OrderPaymentMethod(PaymentMethodId id) {
        this.id = id;
    }
}
