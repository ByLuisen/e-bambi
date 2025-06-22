package com.e.bambi.shared.kernel.domain.event.payload.inventory;

import java.util.UUID;

public class InventoryReservedEventPayload extends InventoryEventPayload {

        public InventoryReservedEventPayload(UUID orderId) {
                super(orderId);
        }
}
