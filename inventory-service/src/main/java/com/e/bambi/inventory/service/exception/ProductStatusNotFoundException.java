package com.e.bambi.inventory.service.exception;

public class ProductStatusNotFoundException extends RuntimeException {
    public ProductStatusNotFoundException(String message) {
        super(message);
    }
}
