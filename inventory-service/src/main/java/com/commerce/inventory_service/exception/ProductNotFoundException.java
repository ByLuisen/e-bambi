package com.commerce.inventory_service.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.lang.Nullable;
import org.springframework.web.ErrorResponseException;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(@Nullable String message) {
        super(message);
    }
}

