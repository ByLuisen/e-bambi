package com.e.bambi.inventory.service.exception;

public class ProductStatusBadRequestException extends RuntimeException {
    public ProductStatusBadRequestException(String message) {
        super(message);
    }
}
