package com.e.bambi.order.application.order.dto.response.orderwithdetails;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class OrderWithDetailReadResponse {
    private final String orderStatus;
    private final OrderWithDetailPaymentMethod paymentMethod;
    private final OrderWithDetailAddress address;
    private final List<OrderWithDetailItem> items;
    private final BigDecimal totalPrice;
    private final OffsetDateTime createdAt;
}
