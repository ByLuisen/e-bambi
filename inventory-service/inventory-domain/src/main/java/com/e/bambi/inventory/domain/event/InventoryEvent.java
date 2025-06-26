package com.e.bambi.inventory.domain.event;

import com.e.bambi.inventory.domain.offer.entity.Offer;
import com.e.bambi.shared.kernel.domain.event.DomainEvent;
import com.e.bambi.shared.kernel.domain.event.payload.inventory.InventoryEventPayload;
import com.e.bambi.shared.kernel.domain.valueobject.OrderId;
import lombok.Getter;

@Getter
public abstract class InventoryEvent implements DomainEvent<Offer> {

    protected final String aggregatetype;
    protected final OrderId orderId;

    protected InventoryEvent(String aggregatetype, OrderId orderId) {
        this.aggregatetype = aggregatetype;
        this.orderId = orderId;
    }

    public abstract InventoryEventPayload toPayload();
}
