package com.e.bambi.order.infrastructure.persistence.outbox.repository;

import com.e.bambi.order.infrastructure.persistence.outbox.entity.OrderOutboxEventEntity;
import lombok.RequiredArgsConstructor;
import org.jooq.Require;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class OrderOutboxEventR2dbcEntityTemplate {

    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    public Mono<OrderOutboxEventEntity> insert(OrderOutboxEventEntity entity) {
        return r2dbcEntityTemplate.insert(entity);
    }
}
