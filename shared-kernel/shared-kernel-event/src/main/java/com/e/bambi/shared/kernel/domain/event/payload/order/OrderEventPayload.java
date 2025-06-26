package com.e.bambi.shared.kernel.domain.event.payload.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public abstract class OrderEventPayload {
    @JsonProperty
    private UUID orderId;

    protected OrderEventPayload(UUID orderId) {
        this.orderId = orderId;
    }
}
