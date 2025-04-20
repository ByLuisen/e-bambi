package com.e.bambi.inventory.service.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderByEnum {
    PRICE("price"),
    CREATED_AT("created_at"),
    QUANTITY("quantity");


    private final String fieldName;

    public static String fromString(String value) {
        for (OrderByEnum field : OrderByEnum.values()) {
            if (field.getFieldName().equals(value)) {
                return field.getFieldName();
            }
        }
        throw new IllegalArgumentException("The request contains invalid order by. Please verify your input and try again.");
    }
}
