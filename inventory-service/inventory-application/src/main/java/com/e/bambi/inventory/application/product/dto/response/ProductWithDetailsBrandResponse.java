package com.e.bambi.inventory.application.product.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class ProductWithDetailsBrandResponse {
    private final UUID id;
    private final String name;
}