package com.e.bambi.inventory.infrastructure.persistence.offer.repository.r2dbc;

import com.e.bambi.inventory.infrastructure.persistence.offer.entity.OfferEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class OfferR2dbcEntityTemplate {

    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    public Mono<OfferEntity> insert(OfferEntity offerEntity) {
        return r2dbcEntityTemplate.insert(offerEntity);
    }
}
