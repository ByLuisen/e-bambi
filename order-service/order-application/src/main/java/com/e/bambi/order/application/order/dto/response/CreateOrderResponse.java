package com.e.bambi.order.application.order.dto.response;

import java.util.UUID;

public record CreateOrderResponse(UUID id, String orderStatus, String message) {
}
