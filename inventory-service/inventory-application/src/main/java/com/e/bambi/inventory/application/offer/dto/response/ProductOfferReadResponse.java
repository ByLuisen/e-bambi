package com.e.bambi.inventory.application.offer.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class ProductOfferReadResponse {
    private final UUID supplierId;
    private final String supplier;
    private final Integer stock;
    private final BigDecimal price;
}
