package com.e.bambi.order.application.message.listener.payment;

import com.e.bambi.order.application.dto.message.PaymentResponse;
import com.e.bambi.order.application.port.inbound.message.listener.payment.PaymentFailedMessageListener;

public class PaymentFailedMessageListenerImpl implements PaymentFailedMessageListener {
    @Override
    public void paymentCompleted(PaymentResponse paymentResponse) {

    }

    @Override
    public void paymentCancelled(PaymentResponse paymentResponse) {

    }
}
