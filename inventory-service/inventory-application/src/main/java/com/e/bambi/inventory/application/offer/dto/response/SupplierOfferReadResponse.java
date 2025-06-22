package com.e.bambi.inventory.application.offer.dto.response;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public class SupplierOfferReadResponse {
    private final SupplierOfferProductResponse product;
    private final Integer stock;
    private final BigDecimal price;
}
