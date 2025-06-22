package com.e.bambi.inventory.domain.exception;

public class InsufficientStockException extends InventoryDomainException {
    public InsufficientStockException(String message) {
        super(message);
    }

    public InsufficientStockException(String message, Throwable cause) {
        super(message, cause);
    }
}
