package com.e.bambi.payment.domain;

import com.e.bambi.payment.domain.event.PaymentMethodValidatedEvent;
import com.e.bambi.shared.kernel.domain.valueobject.OrderId;

public class PaymentDomainServiceImpl implements PaymentDomainService {

    @Override
    public PaymentMethodValidatedEvent validatePayment(String aggregatetype, OrderId orderId) {
        return new PaymentMethodValidatedEvent(aggregatetype, orderId);
    }
}
