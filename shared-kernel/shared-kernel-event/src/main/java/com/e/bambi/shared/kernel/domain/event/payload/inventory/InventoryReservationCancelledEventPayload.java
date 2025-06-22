package com.e.bambi.shared.kernel.domain.event.payload.inventory;

import java.util.UUID;

public class InventoryReservationCancelledEventPayload extends InventoryEventPayload {

    public InventoryReservationCancelledEventPayload(UUID orderId) {
        super(orderId);
    }
}
