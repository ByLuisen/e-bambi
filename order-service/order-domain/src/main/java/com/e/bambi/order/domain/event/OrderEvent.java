package com.e.bambi.order.domain.event;

import com.e.bambi.order.domain.order.entity.Order;
import com.e.bambi.shared.kernel.domain.event.DomainEvent;
import com.e.bambi.shared.kernel.domain.event.payload.order.OrderEventPayload;
import lombok.Getter;

@Getter
public abstract class OrderEvent implements DomainEvent<Order> {
    protected final String aggregatetype;
    protected final Order order;

    protected OrderEvent(String aggregatetype, Order order) {
        this.aggregatetype = aggregatetype;
        this.order = order;
    }

    protected abstract OrderEventPayload toPayload();
}
