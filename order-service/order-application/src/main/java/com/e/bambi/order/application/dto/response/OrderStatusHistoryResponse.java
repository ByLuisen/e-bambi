package com.e.bambi.order.application.dto.response;

import java.time.Instant;

public record OrderStatusHistoryResponse(String orderStatus, String reason, Instant changedAt) {
}
