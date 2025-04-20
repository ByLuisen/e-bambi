package com.e.bambi.order.service.repository;

import com.e.bambi.order.service.dto.queries.OrderQuery;
import com.e.bambi.order.service.dto.queries.PaginatedResultResponse;
import com.e.bambi.order.service.dto.queries.order.OrderResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface OrderRepositoryCustom {

    Flux<OrderResponse> findOrdersByUserId(UUID userId);
    Mono<OrderResponse> findOrderByUserIdAndOrderId(UUID userId, UUID orderId);
    Mono<PaginatedResultResponse> findAllOrders(OrderQuery orderQuery);
    Mono<OrderResponse> findOrderByOrderId(UUID orderId);
}
