package com.e.bambi.inventory.domain.exception;

import com.e.bambi.shared.kernel.domain.exception.DomainException;

public class ProductNotFoundException extends InventoryDomainException {

    public ProductNotFoundException(String message) {
        super(message);
    }

    public ProductNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

