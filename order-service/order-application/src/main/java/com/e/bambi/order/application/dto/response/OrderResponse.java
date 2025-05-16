package com.e.bambi.order.application.dto.response;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public record OrderResponse(UUID orderId, UUID userId, UUID orderStatusId, String orderStatus, OrderAddressResponse address,
                            List<OrderItemResponse> items, String paymentMethod, BigDecimal totalPrice,
                            ZonedDateTime createdAt, ZonedDateTime updatedAt) {
}
