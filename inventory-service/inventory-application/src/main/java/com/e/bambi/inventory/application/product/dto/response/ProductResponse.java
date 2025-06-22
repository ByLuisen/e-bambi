package com.e.bambi.inventory.application.product.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class ProductResponse {
    private final UUID id;
    private final UUID brandId;
    private final UUID departmentId;
    private final UUID productStatusId;
    private final String sku;
    private final String name;
    private final String description;
}
