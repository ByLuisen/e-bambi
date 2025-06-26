package com.e.bambi.order.infrastructure.persistence.order.adapter;

import com.e.bambi.order.application.order.port.outbound.repository.OrderRepository;
import com.e.bambi.order.domain.order.entity.Order;
import com.e.bambi.order.domain.order.valueobject.OrderStatus;
import com.e.bambi.order.infrastructure.persistence.order.entity.OrderEntity;
import com.e.bambi.order.infrastructure.persistence.order.entity.OrderItemEntity;
import com.e.bambi.order.infrastructure.persistence.order.entity.OrderStatusHistoryEntity;
import com.e.bambi.order.infrastructure.persistence.order.mapper.OrderPersistenceMapper;
import com.e.bambi.order.infrastructure.persistence.order.repository.jooq.OrderJooqRepository;
import com.e.bambi.order.infrastructure.persistence.order.repository.r2dbc.*;
import com.e.bambi.shared.kernel.domain.valueobject.OrderId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderR2dbcDatabaseClient orderR2dbcDatabaseClient;
    private final OrderJooqRepository orderJooqRepository;
    private final OrderR2dbcRepository orderR2dbcRepository;
    private final OrderItemR2dbcDatabaseClient orderItemR2dbcDatabaseClient;
    private final OrderStatusHistoryR2dbcDatabaseClient orderStatusHistoryR2dbcDatabaseClient;
    private final OrderPersistenceMapper orderPersistenceMapper;

    @Override
    public Mono<Order> findById(OrderId orderId) {
        return orderR2dbcRepository.findOrderById(orderId.getValue())
                .map(orderPersistenceMapper::toOrder);
    }

    @Override
    public Mono<Order> findOrderWithItems(OrderId orderId) {
        return orderJooqRepository.findOrderWithItems(orderId.getValue());
    }

    @Override
    public Mono<Order> insert(Order order) {
        return orderR2dbcDatabaseClient.insert(orderPersistenceMapper.toOrderEntity(order))
                .flatMap(orderEntity -> {
                    Mono<List<OrderItemEntity>> orderItemEntities =
                            orderItemR2dbcDatabaseClient
                                    .saveAll(orderPersistenceMapper.toOrderItemEntity(order)).collectList();
                    Mono<OrderStatusHistoryEntity> orderStatusHistoryEntity =
                            orderStatusHistoryR2dbcDatabaseClient
                                    .insert(orderPersistenceMapper.toOrderStatusHistoryEntity(order));
                    return Mono.zip(
                            orderStatusHistoryEntity,
                            orderItemEntities
                    ).flatMap(tuple ->
                            Mono.just(orderPersistenceMapper.tupleToOrder(orderEntity, tuple.getT1(), tuple.getT2()))
                    );
                });
    }

    @Override
    public Mono<Order> update(Order order) {
        Mono<OrderEntity> orderEntity =
                orderR2dbcDatabaseClient.update(orderPersistenceMapper.forStatusUpdate(order));
        Mono<OrderStatusHistoryEntity> orderStatusHistory =
                orderStatusHistoryR2dbcDatabaseClient.insert(orderPersistenceMapper.toOrderStatusHistoryEntity(order));
        return Mono.zip(
                orderEntity,
                orderStatusHistory
        ).flatMap(tuple ->
                Mono.just(orderPersistenceMapper.tupleToOrder(tuple.getT1(), tuple.getT2(), null))
        );
    }
}
