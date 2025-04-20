package com.e.bambi.inventory.service.exception;

public class MovementTypeNotFoundException extends RuntimeException {
    public MovementTypeNotFoundException(String message) {
        super(message);
    }
}
