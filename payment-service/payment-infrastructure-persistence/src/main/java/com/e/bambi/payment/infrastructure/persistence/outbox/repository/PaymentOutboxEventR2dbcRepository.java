package com.e.bambi.payment.infrastructure.persistence.outbox.repository;

import com.e.bambi.payment.infrastructure.persistence.outbox.entity.PaymentOutboxEventEntity;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Repository
public interface PaymentOutboxEventR2dbcRepository extends R2dbcRepository<PaymentOutboxEventEntity, UUID> {
    Mono<Boolean> existsByAggregateidAndAggregatetypeIn(String aggregateid, List<String> aggregatetypes);

    @Modifying
    @Query("DELETE FROM payment_outbox_events WHERE id = :paymentOutboxEventId")
    Mono<Integer> deletePaymentOutboxEventById(UUID paymentOutboxEventId);
}
