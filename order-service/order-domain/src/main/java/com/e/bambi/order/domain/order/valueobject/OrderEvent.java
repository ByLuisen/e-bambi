package com.e.bambi.order.domain.order.valueobject;

import lombok.Getter;

@Getter
public enum OrderEvent {

    INVENTORY_RESERVE("inventory.reserve"),
    INVENTORY_CANCEL_RESERVATION("inventory.cancel_reservation"),
    PAYMENT_VALIDATE("payment.validate");

    private final String event;

    OrderEvent(String event) {
        this.event = event;
    }
}
