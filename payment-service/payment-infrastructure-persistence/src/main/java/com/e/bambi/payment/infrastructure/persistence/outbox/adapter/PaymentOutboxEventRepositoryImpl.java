package com.e.bambi.payment.infrastructure.persistence.outbox.adapter;

import com.e.bambi.payment.application.outbox.model.PaymentOutboxEvent;
import com.e.bambi.payment.application.outbox.port.outbound.repository.PaymentOutboxEventRepository;
import com.e.bambi.payment.infrastructure.persistence.outbox.mapper.PaymentOutboxEventPersistenceMapper;
import com.e.bambi.payment.infrastructure.persistence.outbox.repository.PaymentOutboxEventR2dbcDatabaseClient;
import com.e.bambi.payment.infrastructure.persistence.outbox.repository.PaymentOutboxEventR2dbcRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentOutboxEventRepositoryImpl implements PaymentOutboxEventRepository {

    private final PaymentOutboxEventPersistenceMapper paymentOutboxEventPersistenceMapper;
    private final PaymentOutboxEventR2dbcRepository paymentOutboxEventR2dbcRepository;
    private final PaymentOutboxEventR2dbcDatabaseClient paymentOutboxEventR2dbcDatabaseClient;

    @Override
    public Mono<Boolean> existsByAggregateIdAndAggregateTypeIn(String aggregateid, String... aggregatetype) {
        return paymentOutboxEventR2dbcRepository
                .existsByAggregateidAndAggregatetypeIn(
                        aggregateid,
                        Arrays.asList(aggregatetype));
    }

    @Override
    public Mono<PaymentOutboxEvent> insert(PaymentOutboxEvent paymentOutboxEvent) {
        return paymentOutboxEventR2dbcDatabaseClient
                .insert(paymentOutboxEventPersistenceMapper
                        .toPaymentOutboxEventEntity(paymentOutboxEvent)
                )
                .map(paymentOutboxEventPersistenceMapper::toPaymentOutboxEvent);
    }

    @Override
    public Mono<Integer> deleteById(UUID paymentOutboxEventId) {
        return paymentOutboxEventR2dbcRepository
                .deletePaymentOutboxEventById(paymentOutboxEventId);
    }
}
