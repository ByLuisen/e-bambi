package com.e.bambi.payment.infrastructure.messaging.mapper;

import com.e.bambi.payment.application.dto.command.ValidatePaymentCommand;
import com.e.bambi.payment.infrastructure.messaging.dto.listener.ValidatePaymentMessageDTO;
import com.e.bambi.shared.kernel.domain.valueobject.OrderId;
import com.e.bambi.shared.kernel.domain.valueobject.PaymentMethodId;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PaymentMessagingMapper {
    public ValidatePaymentCommand toValidatePaymentCommand(String sagaId, ValidatePaymentMessageDTO payload) {
        return new ValidatePaymentCommand(
                sagaId,
                new OrderId(UUID.fromString(payload.orderId())),
                new PaymentMethodId(UUID.fromString(payload.paymentMethodId()))
        );
    }
}
