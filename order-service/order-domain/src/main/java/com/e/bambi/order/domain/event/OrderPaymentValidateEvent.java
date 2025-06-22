package com.e.bambi.order.domain.event;

import com.e.bambi.order.domain.order.entity.Order;
import com.e.bambi.shared.kernel.domain.event.payload.order.OrderEventPayload;
import com.e.bambi.shared.kernel.domain.event.payload.order.OrderPaymentValidateEventPayload;

public class OrderPaymentValidateEvent extends OrderEvent {

    public OrderPaymentValidateEvent(String aggregatetype, Order order) {
        super(aggregatetype, order);
    }

    @Override
    public OrderEventPayload toPayload() {
        return new OrderPaymentValidateEventPayload(
                order.getId().getValue(),
                order.getPaymentMethod().getId().getValue()
        );
    }
}
