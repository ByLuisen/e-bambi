package com.e.bambi.payment.application.outbox.port.outbound.repository;

import com.e.bambi.payment.application.outbox.model.PaymentOutboxEvent;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface PaymentOutboxEventRepository {
    Mono<Boolean> existsByAggregateIdAndAggregateTypeIn(String aggregateid, String... aggregatetype);
    Mono<PaymentOutboxEvent> insert(PaymentOutboxEvent paymentOutboxEvent);
    Mono<Integer> deleteById(UUID paymentOutboxEventId);
}
