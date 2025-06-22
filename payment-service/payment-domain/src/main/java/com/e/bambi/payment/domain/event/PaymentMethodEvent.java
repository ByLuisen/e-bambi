package com.e.bambi.payment.domain.event;

import com.e.bambi.payment.domain.paymentmethod.entity.PaymentMethod;
import com.e.bambi.shared.kernel.domain.event.DomainEvent;
import com.e.bambi.shared.kernel.domain.event.payload.payment.PaymentMethodEventPayload;
import com.e.bambi.shared.kernel.domain.valueobject.OrderId;
import lombok.Getter;

@Getter
public abstract class PaymentMethodEvent implements DomainEvent<PaymentMethod> {
    protected final String aggregatetype;
    protected final OrderId orderId;

    public PaymentMethodEvent(String aggregatetype, OrderId orderId) {
        this.aggregatetype = aggregatetype;
        this.orderId = orderId;
    }

    public abstract PaymentMethodEventPayload toPayload();
}
