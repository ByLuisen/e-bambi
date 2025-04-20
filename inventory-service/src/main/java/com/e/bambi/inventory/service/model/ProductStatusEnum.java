package com.e.bambi.inventory.service.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public enum ProductStatusEnum {

    INACTIVE(UUID.fromString("ba0a6601-dfe4-485a-83f9-c149db939f2e")),
    ACTIVE(UUID.fromString("1e8b7e83-bf45-4838-80bd-aa9e2cb9c286"));

    private final UUID id;

    public static UUID fromString(String productStatus) {
        for (ProductStatusEnum status : ProductStatusEnum.values()) {
            if (status.name().equals(productStatus)) {
                return status.getId();
            }
        }
        throw new IllegalArgumentException("Status not found: " + productStatus);
    }
}
