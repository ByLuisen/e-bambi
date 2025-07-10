package com.e.bambi.inventory.application.offer.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class ProductOfferSupplierResponse {
    private final UUID id;
    private final String name;
}
