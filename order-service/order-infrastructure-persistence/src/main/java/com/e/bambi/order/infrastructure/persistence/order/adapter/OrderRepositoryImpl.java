package com.e.bambi.order.infrastructure.persistence.order.adapter;

import com.e.bambi.order.infrastructure.persistence.order.mapper.OrderPersistenceMapper;
import com.e.bambi.shared.kernel.domain.valueobject.UserId;
import com.e.bambi.order.outbound.persistence.order.mapper.OrderDataAccessMapper;
import com.e.bambi.order.infrastructure.persistence.order.repository.jooq.OrderJooqRepository;
import com.e.bambi.order.infrastructure.persistence.order.repository.r2dbc.OrderR2dbcRepository;
import com.e.bambi.order.application.dto.query.OrderQuery;
import com.e.bambi.order.domain.entity.Order;
import com.e.bambi.order.application.port.outbound.repository.OrderRepository;
import com.e.bambi.shared.kernel.domain.valueobject.OrderId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderR2dbcRepository orderR2dbcRepository;
    private final OrderJooqRepository orderJooqRepository;
    private final OrderPersistenceMapper orderPersistenceMapper;

    @Override
    public Flux<Order> findByUserId(UserId userId) {
        return orderJooqRepository.findByUserId(userId);
    }

    @Override
    public Mono<Order> findByUserIdAndOrderId(UserId userId, OrderId orderId) {
        return null;
    }

    @Override
    public Flux<Order> findAllByFilter(OrderQuery orderQuery) {
        return null;
    }

    @Override
    public Mono<Order> findByOrderId(OrderId orderId) {
        return null;
    }

    @Override
    public Mono<Order> save(Order order) {
        return orderPersistenceMapper
                .orderEntityToOrder(orderR2dbcRepository
                        .save(orderPersistenceMapper
                                .orderToOrderEntity(order)));
    }
}
