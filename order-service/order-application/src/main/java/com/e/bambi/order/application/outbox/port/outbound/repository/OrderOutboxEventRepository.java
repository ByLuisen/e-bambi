package com.e.bambi.order.application.outbox.port.outbound.repository;

import com.e.bambi.order.application.outbox.model.OrderOutboxEvent;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface OrderOutboxEventRepository {
    Mono<OrderOutboxEvent> insert(OrderOutboxEvent orderOutboxEvent);

    Mono<Integer> deleteById(UUID orderOutboxEventId);
}
