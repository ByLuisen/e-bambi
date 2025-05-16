package com.e.bambi.order.domain.exception;

import com.e.bambi.shared.kernel.domain.exception.DomainException;

public class OrderStatusHistoryNotFoundException extends DomainException {
    public OrderStatusHistoryNotFoundException(String message) {
        super(message);
    }

    public OrderStatusHistoryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
