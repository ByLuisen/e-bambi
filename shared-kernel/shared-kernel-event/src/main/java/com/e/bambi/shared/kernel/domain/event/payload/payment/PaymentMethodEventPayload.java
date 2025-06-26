package com.e.bambi.shared.kernel.domain.event.payload.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public abstract class PaymentMethodEventPayload {
    @JsonProperty
    private UUID orderId;

    protected PaymentMethodEventPayload(UUID orderId) {
        this.orderId = orderId;
    }
}
