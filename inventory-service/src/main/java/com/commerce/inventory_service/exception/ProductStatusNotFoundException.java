package com.commerce.inventory_service.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

public class ProductStatusNotFoundException extends RuntimeException {
    public ProductStatusNotFoundException(String message) {
        super(message);
    }
}
