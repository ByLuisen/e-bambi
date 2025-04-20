package com.e.bambi.order.service.dto.queries;

import java.time.LocalDateTime;

public record StatusHistoryResponse(String name, String reason, LocalDateTime changedAt) {
}
