package com.e.bambi.inventory.infrastructure.messaging.mapper;

import com.e.bambi.shared.kernel.domain.event.payload.order.OrderInventoryCancelReservationEventPayload;
import com.e.bambi.shared.kernel.domain.event.payload.order.OrderInventoryReserveEventPayload;
import org.springframework.stereotype.Component;

@Component
public class InventoryMessagingMapper {

    public ReserveProductCommand toReserveProductCommand(OrderInventoryReserveEventPayload payload, String sagaId) {
        return null;
    }

    public CancelProductReservationCommand toCancelProductReservationCommand(
            OrderInventoryCancelReservationEventPayload payload, String sagaId) {
        return null;
    }

}
