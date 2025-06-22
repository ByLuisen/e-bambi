package com.e.bambi.inventory.domain.exception;

public class ProductStatusNotFoundException extends InventoryDomainException {
    public ProductStatusNotFoundException(String message) {
        super(message);
    }

    public ProductStatusNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
