package com.e.bambi.payment.application.port.outbound.repository;

import com.e.bambi.payment.application.outbox.model.PaymentOutboxEvent;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface PaymentOutboxEventRepository {
    Mono<PaymentOutboxEvent> save(PaymentOutboxEvent paymentOutboxEvent);
    Mono<Integer> deleteById(UUID paymentOutboxEventId);
}
