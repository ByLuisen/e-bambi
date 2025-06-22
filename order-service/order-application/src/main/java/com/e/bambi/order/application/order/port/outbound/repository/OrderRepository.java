package com.e.bambi.order.application.order.port.outbound.repository;

import com.e.bambi.order.domain.order.entity.Order;
import com.e.bambi.shared.kernel.domain.valueobject.OrderId;
import reactor.core.publisher.Mono;

public interface OrderRepository {
    Mono<Order> findById(OrderId orderId);

    Mono<Order> insert(Order order);

    Mono<Order> update(Order order);

}
