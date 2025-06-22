package com.e.bambi.shared.kernel.domain.event.payload.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.UUID;

@Getter
public class PaymentMethodValidatedEventPayload extends PaymentMethodEventPayload {

    public PaymentMethodValidatedEventPayload(UUID orderId) {
        super(orderId);
    }
}
