package com.e.bambi.inventory.domain.exception;

public class BrandNotFoundException extends InventoryDomainException {
    public BrandNotFoundException(String message) {
        super(message);
    }

    public BrandNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
