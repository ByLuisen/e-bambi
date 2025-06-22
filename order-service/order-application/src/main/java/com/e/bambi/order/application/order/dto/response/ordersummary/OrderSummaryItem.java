package com.e.bambi.order.application.order.dto.response.ordersummary;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class OrderSummaryItem {
    private final String imageUrl;
    private final UUID productId;
    private final String name;
}
