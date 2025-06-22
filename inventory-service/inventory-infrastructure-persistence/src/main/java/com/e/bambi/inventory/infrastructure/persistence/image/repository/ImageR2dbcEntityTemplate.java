package com.e.bambi.inventory.infrastructure.persistence.image.repository;

import com.e.bambi.inventory.infrastructure.persistence.image.entity.ImageEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class ImageR2dbcEntityTemplate {

    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    public Mono<ImageEntity> insert(ImageEntity entity) {
        return r2dbcEntityTemplate.insert(entity);
    }
}
