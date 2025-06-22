package com.e.bambi.payment.infrastructure.messaging.paymentmethod.mapper;

import com.e.bambi.payment.application.paymentmethod.dto.command.message.ValidatePaymentCommand;
import com.e.bambi.shared.kernel.domain.event.payload.order.OrderPaymentValidateEventPayload;
import com.e.bambi.shared.kernel.domain.valueobject.OrderId;
import com.e.bambi.shared.kernel.domain.valueobject.PaymentMethodId;
import org.springframework.stereotype.Component;

@Component
public class PaymentMethodMessagingMapper {
    public ValidatePaymentCommand toValidatePaymentCommand(String sagaId, OrderPaymentValidateEventPayload payload) {
        return new ValidatePaymentCommand(
                sagaId,
                new OrderId(payload.getOrderId()),
                new PaymentMethodId(payload.getPaymentMethodId())
        );
    }
}
