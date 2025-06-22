package com.e.bambi.order.domain.exception;

public class OrderStatusHistoryNotFoundException extends OrderDomainException {
    public OrderStatusHistoryNotFoundException(String message) {
        super(message);
    }

    public OrderStatusHistoryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
