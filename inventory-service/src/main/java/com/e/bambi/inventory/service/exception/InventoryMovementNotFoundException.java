package com.e.bambi.inventory.service.exception;

public class InventoryMovementNotFoundException extends RuntimeException {
    public InventoryMovementNotFoundException(String message) {
        super(message);
    }
}
