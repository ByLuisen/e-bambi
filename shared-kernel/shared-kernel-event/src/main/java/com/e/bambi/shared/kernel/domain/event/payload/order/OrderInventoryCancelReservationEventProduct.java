package com.e.bambi.shared.kernel.domain.event.payload.order;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record OrderInventoryCancelReservationEventProduct(
        @JsonProperty
        UUID id,
        @JsonProperty
        UUID supplierId,
        @JsonProperty
        Integer quantity
) {
}
