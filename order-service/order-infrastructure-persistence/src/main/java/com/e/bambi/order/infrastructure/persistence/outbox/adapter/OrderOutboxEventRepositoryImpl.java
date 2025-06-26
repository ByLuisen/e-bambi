package com.e.bambi.order.infrastructure.persistence.outbox.adapter;

import com.e.bambi.order.application.outbox.model.OrderOutboxEvent;
import com.e.bambi.order.application.outbox.port.outbound.repository.OrderOutboxEventRepository;
import com.e.bambi.order.infrastructure.persistence.outbox.mapper.OrderOutboxEventPersistenceMapper;
import com.e.bambi.order.infrastructure.persistence.outbox.repository.OrderOutboxEventR2dbcDatabaseClient;
import com.e.bambi.order.infrastructure.persistence.outbox.repository.OrderOutboxEventR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OrderOutboxEventRepositoryImpl implements OrderOutboxEventRepository {

    private final OrderOutboxEventR2dbcRepository orderOutboxEventR2dbcRepository;
    private final OrderOutboxEventR2dbcDatabaseClient orderOutboxEventR2dbcDatabaseClient;
    private final OrderOutboxEventPersistenceMapper orderOutboxEventPersistenceMapper;

    @Override
    public Mono<OrderOutboxEvent> insert(OrderOutboxEvent orderOutboxEvent) {
        return orderOutboxEventR2dbcDatabaseClient
                .insert(orderOutboxEventPersistenceMapper.toOrderOutboxEventEntity(orderOutboxEvent))
                .map(orderOutboxEventPersistenceMapper::toOrderOutboxEvent);
    }

    @Override
    public Mono<Integer> deleteById(UUID orderOutboxEventId) {
        return orderOutboxEventR2dbcRepository
                .deleteOrderOutboxEventById(orderOutboxEventId);
    }
}
