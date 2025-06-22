package com.e.bambi.shared.kernel.domain.event.payload.inventory;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.UUID;

@Getter
public abstract class InventoryEventPayload {
    @JsonProperty
    private final UUID orderId;

    public InventoryEventPayload(UUID orderId) {
        this.orderId = orderId;
    }
}
