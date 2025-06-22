package com.e.bambi.order.domain.event;

import com.e.bambi.order.domain.order.entity.Order;
import com.e.bambi.shared.kernel.domain.event.payload.order.OrderEventPayload;
import com.e.bambi.shared.kernel.domain.event.payload.order.OrderInventoryCancelReservationEventPayload;
import com.e.bambi.shared.kernel.domain.event.payload.order.OrderInventoryCancelReservationEventProduct;

public class OrderInventoryCancelReservationEvent extends OrderEvent {

    public OrderInventoryCancelReservationEvent(String aggregatetype, Order order) {
        super(aggregatetype, order);
    }

    @Override
    public OrderEventPayload toPayload() {
        return new OrderInventoryCancelReservationEventPayload(
                order.getId().getValue(),
                order.getItems().stream()
                        .map(item ->
                                new OrderInventoryCancelReservationEventProduct(
                                        item.getProduct().getProductId().getValue(),
                                        item.getSupplier().getSupplierId().getValue(),
                                        item.getQuantity()
                                )
                        ).toList()
        );
    }
}
