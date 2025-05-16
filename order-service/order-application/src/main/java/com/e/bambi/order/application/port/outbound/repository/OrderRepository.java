package com.e.bambi.order.application.port.outbound.repository;

import com.e.bambi.shared.kernel.domain.valueobject.UserId;
import com.e.bambi.order.application.dto.query.OrderQuery;
import com.e.bambi.order.domain.entity.Order;
import com.e.bambi.shared.kernel.domain.valueobject.OrderId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderRepository {
    Flux<Order> findByUserId(UserId userId);
    Mono<Order> findByUserIdAndOrderId(UserId userId, OrderId orderId);
    Flux<Order> findAllByFilter(OrderQuery orderQuery);
    Mono<Order> findByOrderId(OrderId orderId);
    Mono<Order> save(Order Order);
}
