package com.e.bambi.inventory.infrastructure.persistence.inventorymovement.repository.r2dbc;

import com.e.bambi.inventory.infrastructure.persistence.inventorymovement.entity.InventoryMovementEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class InventoryMovementR2dbcEntityTemplate {

    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    public Mono<InventoryMovementEntity> insert(InventoryMovementEntity entity) {
        return r2dbcEntityTemplate.insert(entity);
    }
}
