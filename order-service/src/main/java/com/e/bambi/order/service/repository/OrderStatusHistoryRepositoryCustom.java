package com.e.bambi.order.service.repository;

import com.e.bambi.order.service.dto.queries.OrderWithStatusHistoryResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface OrderStatusHistoryRepositoryCustom {
    Mono<OrderWithStatusHistoryResponse> findStatusHistoryByOrderId(UUID orderId);
}