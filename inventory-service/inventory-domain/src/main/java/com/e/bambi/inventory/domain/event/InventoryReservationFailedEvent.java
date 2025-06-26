package com.e.bambi.inventory.domain.event;

import com.e.bambi.shared.kernel.domain.event.payload.inventory.InventoryEventPayload;
import com.e.bambi.shared.kernel.domain.event.payload.inventory.InventoryReservationFailedEventPayload;
import com.e.bambi.shared.kernel.domain.valueobject.OrderId;
import lombok.Getter;

import java.util.List;

@Getter
public class InventoryReservationFailedEvent extends InventoryEvent {

    private final List<String> failureMessages;

    public InventoryReservationFailedEvent(String aggregatetype, OrderId orderId, List<String> failureMessages) {
        super(aggregatetype, orderId);
        this.failureMessages = failureMessages;
    }

    @Override
    public InventoryEventPayload toPayload() {
        return new InventoryReservationFailedEventPayload(
                orderId.getValue(),
                failureMessages
        );
    }
}
