package com.e.bambi.inventory.application.offer.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class SupplierOfferProductResponse {
    private final UUID id;
    private final String sku;
    private final String name;
}