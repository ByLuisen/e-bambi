package com.e.bambi.payment.infrastructure.persistence.outbox.repository;

import com.e.bambi.payment.infrastructure.persistence.outbox.entity.PaymentOutboxEventEntity;
import com.e.bambi.payment.infrastructure.persistence.outbox.mapper.PaymentOutboxEventPersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class PaymentOutboxEventR2dbcDatabaseClient {

    private final DatabaseClient databaseClient;
    private final PaymentOutboxEventPersistenceMapper paymentOutboxEventPersistenceMapper;

    public Mono<PaymentOutboxEventEntity> insert(PaymentOutboxEventEntity paymentOutboxEventEntity) {
        return databaseClient.sql("""
                        INSERT INTO payment_outbox_events (id, aggregatetype, aggregateid, event_type, payload) 
                        VALUES (:id, :aggregatetype, :aggregateid, :event_type, CAST(:payload AS jsonb))
                        RETURNING *
                        """)
                .bind("id", paymentOutboxEventEntity.getId())
                .bind("aggregatetype", paymentOutboxEventEntity.getAggregatetype())
                .bind("aggregateid", paymentOutboxEventEntity.getAggregateid())
                .bind("event_type", paymentOutboxEventEntity.getEventType())
                .bind("payload", paymentOutboxEventEntity.getPayload())
                .map(paymentOutboxEventPersistenceMapper::rowToPaymentOutboxEventEntity)
                .one();
    }
}
