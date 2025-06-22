package com.e.bambi.inventory.infrastructure.persistence.product.repository.r2dbc;

import com.e.bambi.inventory.infrastructure.persistence.product.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class ProductR2dbcEntityTemplate {

    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    public Mono<ProductEntity> insert(ProductEntity entity) {
        return r2dbcEntityTemplate.insert(entity);
    }
}
