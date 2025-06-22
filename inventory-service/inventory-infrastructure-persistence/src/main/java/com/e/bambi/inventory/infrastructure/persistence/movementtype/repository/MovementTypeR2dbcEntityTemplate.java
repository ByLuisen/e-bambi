package com.e.bambi.inventory.infrastructure.persistence.movementtype.repository;

import com.e.bambi.inventory.infrastructure.persistence.movementtype.entity.MovementTypeEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class MovementTypeR2dbcEntityTemplate {

    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    public Mono<MovementTypeEntity> insert(MovementTypeEntity entity) {
        return r2dbcEntityTemplate.insert(entity);
    }
}
