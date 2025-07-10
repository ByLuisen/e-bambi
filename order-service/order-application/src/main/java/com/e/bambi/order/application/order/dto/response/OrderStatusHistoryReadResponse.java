package com.e.bambi.order.application.order.dto.response;

import java.time.OffsetDateTime;

public record OrderStatusHistoryReadResponse(String orderStatus, String reason, OffsetDateTime createdAt) {
}
