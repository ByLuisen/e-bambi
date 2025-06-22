package com.e.bambi.order.application.order.dto.response.orderwithdetails;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class OrderWithDetailReadResponse {
    private final String orderStatus;
    private final OrderWithDetailPaymentMethod paymentMethod;
    private final OrderWithDetailAddress address;
    private final List<OrderWithDetailItem> items;
    private final BigDecimal totalPrice;
    private final Instant createdAt;
}
