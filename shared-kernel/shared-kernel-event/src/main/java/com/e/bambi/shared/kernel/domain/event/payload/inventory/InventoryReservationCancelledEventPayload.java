package com.e.bambi.shared.kernel.domain.event.payload.inventory;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class InventoryReservationCancelledEventPayload extends InventoryEventPayload {

    public InventoryReservationCancelledEventPayload(UUID orderId) {
        super(orderId);
    }
}
