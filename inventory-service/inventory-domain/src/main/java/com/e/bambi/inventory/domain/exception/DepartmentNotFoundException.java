package com.e.bambi.inventory.domain.exception;

public class DepartmentNotFoundException extends InventoryDomainException {
    public DepartmentNotFoundException(String message) {
        super(message);
    }

    public DepartmentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
