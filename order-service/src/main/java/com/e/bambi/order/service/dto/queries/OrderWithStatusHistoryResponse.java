package com.e.bambi.order.service.dto.queries;

import java.util.List;
import java.util.UUID;

public record OrderWithStatusHistoryResponse(UUID orderId, List<StatusHistoryResponse> statuses) {
}
