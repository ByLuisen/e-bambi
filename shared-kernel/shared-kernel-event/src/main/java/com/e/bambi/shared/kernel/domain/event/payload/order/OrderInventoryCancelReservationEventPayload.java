package com.e.bambi.shared.kernel.domain.event.payload.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class OrderInventoryCancelReservationEventPayload extends OrderEventPayload {

    @JsonProperty
    private List<OrderInventoryCancelReservationEventProduct> products;

    public OrderInventoryCancelReservationEventPayload(UUID orderId,
                                                       List<OrderInventoryCancelReservationEventProduct> products) {
        super(orderId);
        this.products = products;
    }
}