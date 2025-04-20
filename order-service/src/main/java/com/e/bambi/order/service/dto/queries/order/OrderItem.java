package com.e.bambi.order.service.dto.queries.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record OrderItem(UUID orderId, UUID productId, String sku, String name, String soldBy, Integer quantity,
                            BigDecimal price, BigDecimal totalPrice, BigDecimal discount, LocalDateTime createdAt) {
}