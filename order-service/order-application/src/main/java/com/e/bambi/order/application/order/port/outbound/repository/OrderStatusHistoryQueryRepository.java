package com.e.bambi.order.application.order.port.outbound.repository;

import com.e.bambi.order.application.order.dto.response.OrderStatusHistoryReadResponse;
import com.e.bambi.order.domain.order.valueobject.OrderStatus;
import com.e.bambi.shared.kernel.domain.valueobject.OrderId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderStatusHistoryQueryRepository {

    Flux<OrderStatusHistoryReadResponse> findByOrderId(OrderId orderId);

    Mono<Boolean> existsByOrderIdAndOrderStatus(OrderId orderId, OrderStatus orderStatus);

}
