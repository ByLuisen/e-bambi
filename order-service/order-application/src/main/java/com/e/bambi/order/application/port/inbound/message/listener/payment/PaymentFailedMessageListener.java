package com.e.bambi.order.application.port.inbound.message.listener.payment;

import com.e.bambi.order.application.dto.message.PaymentResponse;

public interface PaymentFailedMessageListener {
    void paymentCompleted(PaymentResponse paymentResponse);
    void paymentCancelled(PaymentResponse paymentResponse);
}
