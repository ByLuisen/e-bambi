package com.e.bambi.inventory.application.outbox.model;

import lombok.Getter;

@Getter
public enum InventoryAggregateType {
    RESERVED("reserved"),
    RESERVATION_FAILED("reservation_failed");

    private final String value;

    InventoryAggregateType(String value) {
        this.value = value;
    }
}
