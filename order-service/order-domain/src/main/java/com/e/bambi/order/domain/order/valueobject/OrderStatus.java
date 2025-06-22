package com.e.bambi.order.domain.order.valueobject;

import com.e.bambi.shared.kernel.application.saga.SagaStatus;
import lombok.Getter;

@Getter
public enum OrderStatus {
    PENDING("Started the order creation process"),
    PRODUCTS_RESERVED("Products reserved correctly"),
    CREATED("The order has been created successfully"),
    CANCELLED("The order has been cancelled successfully"),
    CANCELLING("Initiated the process to cancel the order");

    private final String reason;

    OrderStatus(String reason) {
        this.reason = reason;
    }
}
