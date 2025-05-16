package com.e.bambi.order.application.port.outbound.repository;

import com.e.bambi.order.domain.entity.OrderStatusHistory;
import com.e.bambi.shared.kernel.domain.valueobject.OrderId;
import reactor.core.publisher.Flux;

public interface OrderStatusHistoryRepository {
    Flux<OrderStatusHistory> findByOrderId(OrderId orderId);
}
