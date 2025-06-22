package com.e.bambi.payment.domain.event;

import com.e.bambi.shared.kernel.domain.event.payload.payment.PaymentMethodValidationFailedEventPayload;
import com.e.bambi.shared.kernel.domain.valueobject.OrderId;
import lombok.Getter;

import java.util.List;

@Getter
public class PaymentMethodValidationFailedEvent extends PaymentMethodEvent {

    private final List<String> failureMessages;

    public PaymentMethodValidationFailedEvent(String aggregatetype, OrderId orderId,
                                              List<String> failureMessages) {
        super(aggregatetype, orderId);
        this.failureMessages = failureMessages;
    }

    @Override
    public PaymentMethodValidationFailedEventPayload toPayload() {
        return new PaymentMethodValidationFailedEventPayload(
                orderId.getValue(),
                failureMessages
        );
    }
}
