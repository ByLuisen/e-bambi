package com.e.bambi.shared.kernel.domain.event.payload.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class PaymentMethodValidationFailedEventPayload extends PaymentMethodEventPayload {
    @JsonProperty
    private final List<String> failureMessages;

    public PaymentMethodValidationFailedEventPayload(UUID orderId, List<String> failureMessages) {
        super(orderId);
        this.failureMessages = failureMessages;
    }
}
