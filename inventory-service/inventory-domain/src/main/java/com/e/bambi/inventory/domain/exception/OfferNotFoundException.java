package com.e.bambi.inventory.domain.exception;

import com.e.bambi.shared.kernel.domain.exception.DomainException;

public class OfferNotFoundException extends InventoryDomainException {
    public OfferNotFoundException(String message) {
        super(message);
    }

    public OfferNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
