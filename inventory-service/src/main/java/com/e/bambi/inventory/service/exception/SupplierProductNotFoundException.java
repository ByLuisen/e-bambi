package com.e.bambi.inventory.service.exception;

public class SupplierProductNotFoundException extends RuntimeException {
    public SupplierProductNotFoundException(String message) {
        super(message);
    }
}
