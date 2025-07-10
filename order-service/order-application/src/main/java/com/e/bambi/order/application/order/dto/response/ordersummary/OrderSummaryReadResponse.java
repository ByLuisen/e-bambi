package com.e.bambi.order.application.order.dto.response.ordersummary;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class OrderSummaryReadResponse {
    private final UUID id;
    private final String orderStatus;
    private final OrderSummaryAddress address;
    private final List<OrderSummaryItem> items;
    private final BigDecimal totalPrice;
    private final OffsetDateTime createdAt;
}
