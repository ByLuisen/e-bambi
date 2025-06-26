package com.e.bambi.payment.domain.event;

import com.e.bambi.shared.kernel.domain.event.payload.payment.PaymentMethodEventPayload;
import com.e.bambi.shared.kernel.domain.event.payload.payment.PaymentMethodValidatedEventPayload;
import com.e.bambi.shared.kernel.domain.valueobject.OrderId;
import lombok.Getter;

@Getter
public class PaymentMethodValidatedEvent extends PaymentMethodEvent {

    public PaymentMethodValidatedEvent(String aggregatetype, OrderId orderId) {
        super(aggregatetype, orderId);
    }

    @Override
    public PaymentMethodEventPayload toPayload() {
        return new PaymentMethodValidatedEventPayload(
                orderId.getValue()
        );
    }
}
