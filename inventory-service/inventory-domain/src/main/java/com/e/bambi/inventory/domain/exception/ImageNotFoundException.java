package com.e.bambi.inventory.domain.exception;

import com.e.bambi.shared.kernel.domain.exception.DomainException;

public class ImageNotFoundException extends InventoryDomainException {
    public ImageNotFoundException(String message) {
        super(message);
    }

    public ImageNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
