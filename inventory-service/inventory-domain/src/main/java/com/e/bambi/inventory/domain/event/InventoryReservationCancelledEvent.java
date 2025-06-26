package com.e.bambi.inventory.domain.event;

import com.e.bambi.shared.kernel.domain.event.payload.inventory.InventoryEventPayload;
import com.e.bambi.shared.kernel.domain.event.payload.inventory.InventoryReservationCancelledEventPayload;
import com.e.bambi.shared.kernel.domain.valueobject.OrderId;

public class InventoryReservationCancelledEvent extends InventoryEvent {

    public InventoryReservationCancelledEvent(String aggregatetype, OrderId orderId) {
        super(aggregatetype, orderId);
    }

    @Override
    public InventoryEventPayload toPayload() {
        return new InventoryReservationCancelledEventPayload(
                orderId.getValue()
        );
    }
}
