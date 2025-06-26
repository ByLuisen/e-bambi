package com.e.bambi.inventory.domain.event;

import com.e.bambi.shared.kernel.domain.event.payload.inventory.InventoryEventPayload;
import com.e.bambi.shared.kernel.domain.event.payload.inventory.InventoryReservedEventPayload;
import com.e.bambi.shared.kernel.domain.valueobject.OrderId;

public class InventoryReservedEvent extends InventoryEvent {

    public InventoryReservedEvent(String aggregatetype, OrderId orderId) {
        super(aggregatetype, orderId);
    }

    @Override
    public InventoryEventPayload toPayload() {
        return new InventoryReservedEventPayload(
                orderId.getValue()
        );
    }
}
