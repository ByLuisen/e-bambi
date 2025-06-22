package com.e.bambi.inventory.domain.exception;

public class MovementTypeNotFoundException extends InventoryDomainException {
    public MovementTypeNotFoundException(String message) {
        super(message);
    }

    public MovementTypeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
