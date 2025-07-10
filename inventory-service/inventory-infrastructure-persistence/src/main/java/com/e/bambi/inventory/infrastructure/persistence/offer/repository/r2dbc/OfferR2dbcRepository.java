package com.e.bambi.inventory.infrastructure.persistence.offer.repository.r2dbc;

import com.e.bambi.inventory.infrastructure.persistence.offer.entity.OfferEntity;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface OfferR2dbcRepository extends R2dbcRepository<OfferEntity, UUID> {

    @Modifying
    @Query("DELETE FROM offers WHERE id = :offerId AND supplierId = :supplierId")
    Mono<Integer> deleteByIdAndSupplierId(UUID offerId, UUID supplierId);

    Mono<OfferEntity> findBySupplierIdAndProductId(UUID supplierId, UUID productId);
}
