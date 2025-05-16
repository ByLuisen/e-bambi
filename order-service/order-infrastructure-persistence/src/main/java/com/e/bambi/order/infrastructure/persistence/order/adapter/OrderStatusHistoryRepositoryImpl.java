package com.e.bambi.order.infrastructure.persistence.order.adapter;

import com.e.bambi.order.application.port.outbound.repository.OrderStatusHistoryRepository;
import com.e.bambi.order.domain.entity.OrderStatusHistory;
import com.e.bambi.shared.kernel.domain.valueobject.OrderId;
import com.e.bambi.order.infrastructure.persistence.order.mapper.OrderPersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
public class OrderStatusHistoryRepositoryImpl implements OrderStatusHistoryRepository {

    private final OrderStatusHistoryRepository orderStatusHistoryRepository;
    private final OrderPersistenceMapper orderPersistenceMapper;

    @Override
    public Flux<OrderStatusHistory> findByOrderId(OrderId orderId) {
        return null;
    }
}
