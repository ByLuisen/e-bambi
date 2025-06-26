package com.e.bambi.inventory.infrastructure.messaging.mapper;

import com.e.bambi.inventory.application.offer.dto.command.message.CancelReservationInventoryCommand;
import com.e.bambi.inventory.application.offer.dto.command.message.CancelReservationInventoryProduct;
import com.e.bambi.inventory.application.offer.dto.command.message.ReserveInventoryCommand;
import com.e.bambi.inventory.application.offer.dto.command.message.ReserveInventoryProduct;
import com.e.bambi.inventory.domain.shared.valueobject.Stock;
import com.e.bambi.shared.kernel.domain.event.payload.order.OrderInventoryCancelReservationEventPayload;
import com.e.bambi.shared.kernel.domain.event.payload.order.OrderInventoryReserveEventPayload;
import com.e.bambi.shared.kernel.domain.valueobject.Money;
import com.e.bambi.shared.kernel.domain.valueobject.OrderId;
import com.e.bambi.shared.kernel.domain.valueobject.ProductId;
import com.e.bambi.shared.kernel.domain.valueobject.SupplierId;
import org.springframework.stereotype.Component;

@Component
public class InventoryMessagingMapper {

    public ReserveInventoryCommand toReserveInventoryCommand(OrderInventoryReserveEventPayload payload, String sagaId) {
        return new ReserveInventoryCommand(
                sagaId,
                new OrderId(payload.getOrderId()),
                payload.getProducts().stream()
                        .map(product ->
                                new ReserveInventoryProduct(
                                        new ProductId(product.id()),
                                        new SupplierId(product.supplierId()),
                                        new Stock(product.quantity()),
                                        new Money(product.price())
                                )
                        ).toList()
        );
    }

    public CancelReservationInventoryCommand toCancelReservationInventoryCommand(
            OrderInventoryCancelReservationEventPayload payload, String sagaId) {
        return new CancelReservationInventoryCommand(
                sagaId,
                new OrderId(payload.getOrderId()),
                payload.getProducts().stream()
                        .map(product ->
                                new CancelReservationInventoryProduct(
                                        new ProductId(product.id()),
                                        new SupplierId(product.supplierId()),
                                        new Stock(product.quantity())
                                )
                        ).toList()
        );
    }

}
