package com.commerce.inventory_service.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

public class InventoryMovementBadRequestException extends ErrorResponseException {

    public InventoryMovementBadRequestException(String message, Object[] errors) {
        super(HttpStatusCode.valueOf(400), ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), message), null, null, errors);
    }
}
