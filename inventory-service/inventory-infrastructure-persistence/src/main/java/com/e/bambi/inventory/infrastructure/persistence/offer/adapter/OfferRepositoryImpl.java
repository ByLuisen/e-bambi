package com.e.bambi.inventory.infrastructure.persistence.offer.adapter;

import com.e.bambi.inventory.application.offer.port.outbound.repository.OfferRepository;
import com.e.bambi.inventory.domain.offer.entity.Offer;
import com.e.bambi.inventory.domain.offer.valueobject.OfferId;
import com.e.bambi.inventory.infrastructure.persistence.offer.mapper.OfferPersistenceMapper;
import com.e.bambi.inventory.infrastructure.persistence.offer.repository.r2dbc.OfferR2dbcEntityTemplate;
import com.e.bambi.inventory.infrastructure.persistence.offer.repository.r2dbc.OfferR2dbcRepository;
import com.e.bambi.shared.kernel.domain.valueobject.ProductId;
import com.e.bambi.shared.kernel.domain.valueobject.SupplierId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class OfferRepositoryImpl implements OfferRepository {

    private final OfferR2dbcRepository offerR2dbcRepository;
    private final OfferR2dbcEntityTemplate offerR2dbcEntityTemplate;
    private final OfferPersistenceMapper offerPersistenceMapper;

    @Override
    public Mono<Offer> findBySupplierIdAndProductId(SupplierId supplierId, ProductId productId) {
        return offerR2dbcRepository.findBySupplierIdAndProductId(supplierId.getValue(), productId.getValue())
                .map(offerPersistenceMapper::toOffer);
    }

    @Override
    public Mono<Offer> insert(Offer offer) {
        return offerR2dbcEntityTemplate
                .insert(offerPersistenceMapper.toOfferEntity(offer))
                .map(offerPersistenceMapper::toOffer);
    }

    @Override
    public Mono<Offer> update(Offer offer) {
        return offerR2dbcRepository
                .save(offerPersistenceMapper.toOfferEntity(offer))
                .map(offerPersistenceMapper::toOffer);
    }

    @Override
    public Mono<Integer> deleteByIdAndSupplierId(OfferId offerId, SupplierId supplierId) {
        return offerR2dbcRepository
                .deleteByIdAndSupplierId(offerId.getValue(), supplierId.getValue());
    }
}
