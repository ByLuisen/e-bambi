package com.e.bambi.inventory.domain.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class ProductBadRequestException extends InventoryDomainException {

    private List<String> errors;

    public ProductBadRequestException(String message) {
        super(message);
    }

    public ProductBadRequestException(String message, List<String> errors) {
        super(message);
        this.errors = errors;
    }
}
