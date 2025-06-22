package com.e.bambi.payment.domain;

import com.e.bambi.payment.domain.event.PaymentMethodValidatedEvent;
import com.e.bambi.shared.kernel.domain.valueobject.OrderId;

public interface PaymentDomainService {

    public PaymentMethodValidatedEvent validatePayment(String aggregatetype, OrderId orderId);

}
