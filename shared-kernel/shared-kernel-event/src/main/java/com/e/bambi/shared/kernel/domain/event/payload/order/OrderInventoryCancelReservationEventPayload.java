package com.e.bambi.shared.kernel.domain.event.payload.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class OrderInventoryCancelReservationEventPayload extends OrderEventPayload {

    @JsonProperty
    private final List<OrderInventoryCancelReservationEventProduct> products;

    public OrderInventoryCancelReservationEventPayload(UUID orderId,
                                                       List<OrderInventoryCancelReservationEventProduct> products) {
        super(orderId);
        this.products = products;
    }
}