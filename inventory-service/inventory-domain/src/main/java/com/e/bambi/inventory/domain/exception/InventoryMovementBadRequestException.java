package com.e.bambi.inventory.domain.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class InventoryMovementBadRequestException extends InventoryDomainException {

    private List<String> errors;

    public InventoryMovementBadRequestException(String message) {
        super(message);
    }

    public InventoryMovementBadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public InventoryMovementBadRequestException(String message, List<String> errors) {
        super(message);
        this.errors = errors;
    }
}
