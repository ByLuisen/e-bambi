package com.e.bambi.order.application.order.dto.response.orderwithdetails;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class OrderWithDetailItem {
    private final String imageUrl;
    private final OrderWithDetailItemSupplier supplier;
    private final OrderWithDetailItemProduct product;
    private final BigDecimal price;
    private final Integer quantity;
    private final BigDecimal totalPrice;
}
