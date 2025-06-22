package com.e.bambi.payment.application.outbox.model;

import lombok.Getter;

@Getter
public enum PaymentAggregateType {
    VALIDATED("validated"),
    VALIDATION_FAILED("validation_failed");

    private final String value;

    PaymentAggregateType(String value) {
        this.value = value;
    }
}
