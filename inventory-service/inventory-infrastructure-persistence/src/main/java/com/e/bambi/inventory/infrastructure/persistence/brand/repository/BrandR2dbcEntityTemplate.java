package com.e.bambi.inventory.infrastructure.persistence.brand.repository;

import com.e.bambi.inventory.infrastructure.persistence.brand.entity.BrandEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class BrandR2dbcEntityTemplate {

    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    public Mono<BrandEntity> insert(BrandEntity brandEntity) {
        return r2dbcEntityTemplate.insert(brandEntity);
    }
}
