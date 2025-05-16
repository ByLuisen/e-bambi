package com.commerce.inventory_service.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

public class ProductBadRequestException extends ErrorResponseException {
    public ProductBadRequestException(String message, Object[] errors) {
        super(HttpStatusCode.valueOf(400), ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), message), null, null, errors);
    }
}
