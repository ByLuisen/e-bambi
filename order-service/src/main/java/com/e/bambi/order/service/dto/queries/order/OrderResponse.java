package com.e.bambi.order.service.dto.queries.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderResponse(UUID orderId, UUID userId, UUID statusId, String status, OrderAddress address, List<OrderItem> items,
                                     String paymentMethod, BigDecimal totalPrice, LocalDateTime createdAt, LocalDateTime updatedAt) {
}
