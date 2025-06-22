package com.e.bambi.shared.kernel.domain.event.payload.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.UUID;

@Getter
public class OrderPaymentValidateEventPayload extends OrderEventPayload {
    @JsonProperty
    UUID paymentMethodId;

    public OrderPaymentValidateEventPayload(UUID orderId, UUID paymentMethodId) {
        super(orderId);
        this.paymentMethodId = paymentMethodId;
    }
}
