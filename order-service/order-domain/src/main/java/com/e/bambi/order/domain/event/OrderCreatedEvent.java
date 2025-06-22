package com.e.bambi.order.domain.event;

import com.e.bambi.order.domain.order.entity.Order;
import com.e.bambi.shared.kernel.domain.event.payload.order.OrderEventPayload;

public class OrderCreatedEvent extends OrderEvent {

    public OrderCreatedEvent(String aggregatetype, Order order) {
        super(aggregatetype, order);
    }

    @Override
    protected OrderEventPayload toPayload() {
        return null;
    }
}
