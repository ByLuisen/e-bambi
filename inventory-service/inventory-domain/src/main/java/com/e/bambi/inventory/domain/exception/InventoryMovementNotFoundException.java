package com.e.bambi.inventory.domain.exception;

public class InventoryMovementNotFoundException extends InventoryDomainException {
    public InventoryMovementNotFoundException(String message) {
        super(message);
    }

    public InventoryMovementNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
