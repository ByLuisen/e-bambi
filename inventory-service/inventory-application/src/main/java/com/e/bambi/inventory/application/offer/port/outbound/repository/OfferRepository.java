package com.e.bambi.inventory.application.offer.port.outbound.repository;

import com.e.bambi.inventory.domain.offer.entity.Offer;
import com.e.bambi.inventory.domain.offer.valueobject.OfferId;
import com.e.bambi.shared.kernel.domain.valueobject.SupplierId;
import reactor.core.publisher.Mono;

public interface OfferRepository {
    Mono<Offer> insert(Offer offer);

    Mono<Offer> update(Offer offer);

    Mono<Integer> deleteByIdAndSupplierId(OfferId offerId, SupplierId supplierId);
}
