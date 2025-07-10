package com.e.bambi.inventory.infrastructure.persistence.outbox.repository;

import com.e.bambi.inventory.infrastructure.persistence.outbox.entity.InventoryOutboxEventEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class InventoryOutboxEventR2dbcEntityTemplate {

    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    public Mono<InventoryOutboxEventEntity> insert(InventoryOutboxEventEntity entity) {
        return r2dbcEntityTemplate.insert(entity);
    }
}
