package com.e.bambi.shared.kernel.domain.event.payload.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class PaymentMethodValidationFailedEventPayload extends PaymentMethodEventPayload {
    @JsonProperty
    private List<String> failureMessages;

    public PaymentMethodValidationFailedEventPayload(UUID orderId, List<String> failureMessages) {
        super(orderId);
        this.failureMessages = failureMessages;
    }
}
