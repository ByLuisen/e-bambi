package com.e.bambi.order.domain.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class OrderBadRequestException extends OrderDomainException {

    private List<String> errors;

    public OrderBadRequestException(String message) {
        super(message);
    }

    public OrderBadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderBadRequestException(String message, List<String> errors) {
        super(message);
        this.errors = errors;
    }
}
