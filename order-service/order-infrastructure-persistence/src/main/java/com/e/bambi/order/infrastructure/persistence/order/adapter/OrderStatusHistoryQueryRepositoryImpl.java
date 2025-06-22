package com.e.bambi.order.infrastructure.persistence.order.adapter;

import com.e.bambi.order.application.order.dto.response.OrderStatusHistoryReadResponse;
import com.e.bambi.order.application.order.port.outbound.repository.OrderStatusHistoryQueryRepository;
import com.e.bambi.order.domain.order.valueobject.OrderStatus;
import com.e.bambi.order.infrastructure.persistence.order.repository.r2dbc.OrderStatusHistoryR2dbcRepository;
import com.e.bambi.shared.kernel.domain.valueobject.OrderId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class OrderStatusHistoryQueryRepositoryImpl implements OrderStatusHistoryQueryRepository {

    private final OrderStatusHistoryR2dbcRepository orderStatusHistoryR2dbcRepository;

    @Override
    public Flux<OrderStatusHistoryReadResponse> findByOrderId(OrderId orderId) {
        return orderStatusHistoryR2dbcRepository
                .findByOrderId(orderId.getValue());
    }

    @Override
    public Mono<Boolean> existsByOrderIdAndOrderStatus(OrderId orderId, OrderStatus orderStatus) {
        return orderStatusHistoryR2dbcRepository
                .existsByOrderIdAndOrderStatus(orderId.getValue(), orderStatus);
    }
}
