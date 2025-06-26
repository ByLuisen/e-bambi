package com.e.bambi.order.infrastructure.persistence.outbox.repository;

import com.e.bambi.order.infrastructure.persistence.outbox.entity.OrderOutboxEventEntity;
import com.e.bambi.order.infrastructure.persistence.outbox.mapper.OrderOutboxEventPersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class OrderOutboxEventR2dbcDatabaseClient {

    private final DatabaseClient databaseClient;
    private final OrderOutboxEventPersistenceMapper orderOutboxEventPersistenceMapper;

    public Mono<OrderOutboxEventEntity> insert(OrderOutboxEventEntity entity) {
        return databaseClient.sql("""
                        INSERT INTO order_outbox_events
                        VALUES (:id, :aggregatetype, :aggregateid, :eventType, :sagaStatus, CAST(:payload AS jsonb))
                        RETURNING *
                        """)
                .bind("id", entity.getId())
                .bind("aggregatetype", entity.getAggregatetype())
                .bind("aggregateid", entity.getAggregateid())
                .bind("eventType", entity.getEventType())
                .bind("sagaStatus", entity.getSagaStatus().name())
                .bind("payload", entity.getPayload())
                .map(orderOutboxEventPersistenceMapper::rowToOrderOutboxEventEntity)
                .one();
    }
}