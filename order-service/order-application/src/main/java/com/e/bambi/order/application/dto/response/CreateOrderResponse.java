package com.e.bambi.order.application.dto.response;

import java.util.UUID;

public record CreateOrderResponse(UUID orderTrackingId, String orderStatus, String message) {
}
