package com.e.bambi.order.application.dto.response;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

public record OrderItemResponse(UUID orderId, UUID productId, String sku, String name, String soldBy, Integer quantity,
                                BigDecimal price, BigDecimal totalPrice, BigDecimal discount, ZonedDateTime createdAt) {
}