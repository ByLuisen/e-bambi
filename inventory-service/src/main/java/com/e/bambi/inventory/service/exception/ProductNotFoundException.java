package com.e.bambi.inventory.service.exception;

import org.springframework.lang.Nullable;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(@Nullable String message) {
        super(message);
    }
}

