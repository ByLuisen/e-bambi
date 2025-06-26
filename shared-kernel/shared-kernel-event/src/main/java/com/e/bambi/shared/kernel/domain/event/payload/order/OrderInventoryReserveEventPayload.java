package com.e.bambi.shared.kernel.domain.event.payload.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class OrderInventoryReserveEventPayload extends OrderEventPayload {
    @JsonProperty
    private List<OrderInventoryReserveEventProduct> products;

    public OrderInventoryReserveEventPayload(UUID orderId, List<OrderInventoryReserveEventProduct> products) {
        super(orderId);
        this.products = products;
    }
}