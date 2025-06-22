package com.e.bambi.order.application.order.dto.response;

import java.time.Instant;

public record OrderStatusHistoryReadResponse(String orderStatus, String reason, Instant changedAt) {
}
