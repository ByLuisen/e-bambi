package com.e.bambi.order.infrastructure.persistence.order.adapter;

import com.e.bambi.order.infrastructure.persistence.order.repository.r2dbc.OrderItemR2dbcClientRepository;
import com.e.bambi.order.domain.entity.OrderItem;
import com.e.bambi.order.application.port.outbound.repository.OrderItemRepository;
import com.e.bambi.shared.kernel.domain.valueobject.OrderId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderItemRepositoryImpl implements OrderItemRepository {

    private final OrderItemR2dbcClientRepository orderItemJooqRepository;

    @Override
    public Flux<OrderItem> saveAll(List<OrderItem> items, OrderId orderId) {
        return orderItemJooqRepository.saveAll(items, orderId);
    }
}
