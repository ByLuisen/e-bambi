package com.e.bambi.shared.kernel.domain.event.payload.inventory;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public abstract class InventoryEventPayload {
    @JsonProperty
    private UUID orderId;

    protected InventoryEventPayload(UUID orderId) {
        this.orderId = orderId;
    }
}
