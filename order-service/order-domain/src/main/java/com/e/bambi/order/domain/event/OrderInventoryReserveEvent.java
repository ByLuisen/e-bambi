package com.e.bambi.order.domain.event;

import com.e.bambi.order.domain.order.entity.Order;
import com.e.bambi.shared.kernel.domain.event.payload.order.OrderEventPayload;
import com.e.bambi.shared.kernel.domain.event.payload.order.OrderInventoryReserveEventPayload;
import com.e.bambi.shared.kernel.domain.event.payload.order.OrderInventoryReserveEventProduct;

public class OrderInventoryReserveEvent extends OrderEvent {

    public OrderInventoryReserveEvent(String aggregatetype, Order order) {
        super(aggregatetype, order);
    }

    @Override
    public OrderEventPayload toPayload() {
        return new OrderInventoryReserveEventPayload(
                order.getId().getValue(),
                order.getItems().stream()
                        .map(item ->
                                new OrderInventoryReserveEventProduct(
                                        item.getProduct().getProductId().getValue(),
                                        item.getSupplier().getSupplierId().getValue(),
                                        item.getQuantity(),
                                        item.getPrice().getAmount()
                                )
                        ).toList()
        );
    }
}
