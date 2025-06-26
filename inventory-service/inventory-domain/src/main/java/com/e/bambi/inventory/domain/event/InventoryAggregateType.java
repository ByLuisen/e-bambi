package com.e.bambi.inventory.domain.event;

import lombok.Getter;

@Getter
public enum InventoryAggregateType {
    RESERVED("reserved"),
    RESERVATION_FAILED("reservation_failed"),
    RESERVATION_CANCELLED("reservation_cancelled");

    private final String value;

    InventoryAggregateType(String value) {
        this.value = value;
    }
}
