package com.e.bambi.shared.kernel.domain.event.payload.inventory;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class InventoryReservedEventPayload extends InventoryEventPayload {

        public InventoryReservedEventPayload(UUID orderId) {
                super(orderId);
        }
}
