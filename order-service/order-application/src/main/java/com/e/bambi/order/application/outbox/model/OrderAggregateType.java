package com.e.bambi.order.application.outbox.model;

import lombok.Getter;

@Getter
public enum OrderAggregateType {
    INVENTORY_RESERVE("inventory.reserve"),
    INVENTORY_CANCEL_RESERVATION("inventory.cancel_reservation"),
    PAYMENT_VALIDATE("payment.validate");

    private final String value;

    OrderAggregateType(String value) {
        this.value = value;
    }
}
