package com.e.bambi.order.service.exception;

public class OrderStatusHistoryNotFoundException extends RuntimeException {
    public OrderStatusHistoryNotFoundException(String message) {
        super(message);
    }
}
