package com.e.bambi.shared.kernel.domain.event.payload.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.UUID;

@Getter
public abstract class PaymentMethodEventPayload {
    @JsonProperty
    private final UUID orderId;

    protected PaymentMethodEventPayload(UUID orderId) {
        this.orderId = orderId;
    }
}
