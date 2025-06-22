package com.e.bambi.inventory.domain.exception;

public class SupplierNotFoundException extends InventoryDomainException {
    public SupplierNotFoundException(String message) {
        super(message);
    }

    public SupplierNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
