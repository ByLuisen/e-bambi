package com.e.bambi.inventory.application.product.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class ProductSummaryReadResponse {
    private final UUID id;
    private final String sku;
    private final String name;
    private final String brand;
    private final String imageUrl;
    private final OffsetDateTime createdAt;
}
