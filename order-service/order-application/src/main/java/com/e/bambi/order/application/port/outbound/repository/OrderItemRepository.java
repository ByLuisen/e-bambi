package com.e.bambi.order.application.port.outbound.repository;

import com.e.bambi.order.domain.entity.OrderItem;
import com.e.bambi.shared.kernel.domain.valueobject.OrderId;
import reactor.core.publisher.Flux;

import java.util.List;

public interface OrderItemRepository {
    Flux<OrderItem> saveAll(List<OrderItem> items, OrderId orderId);
}
