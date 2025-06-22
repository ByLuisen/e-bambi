package com.e.bambi.inventory.domain.exception;

import com.e.bambi.shared.kernel.domain.exception.DomainException;

public class InventoryDomainException extends DomainException {
    public InventoryDomainException(String message) {
        super(message);
    }

    public InventoryDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
