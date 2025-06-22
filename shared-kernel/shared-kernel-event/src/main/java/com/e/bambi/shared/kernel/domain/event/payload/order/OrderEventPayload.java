package com.e.bambi.shared.kernel.domain.event.payload.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.UUID;

@Getter
public abstract class OrderEventPayload {
    @JsonProperty
    private final UUID orderId;

    protected OrderEventPayload(UUID orderId) {
        this.orderId = orderId;
    }
}
