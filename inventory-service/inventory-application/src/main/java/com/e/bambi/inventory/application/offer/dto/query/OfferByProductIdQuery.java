package com.e.bambi.inventory.application.offer.dto.query;

import com.e.bambi.inventory.application.offer.dto.response.ProductOfferReadResponse;
import com.e.bambi.shared.kernel.application.bus.Query;
import com.e.bambi.shared.kernel.domain.valueobject.ProductId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@Getter
@RequiredArgsConstructor
public class OfferByProductIdQuery extends Query<Flux<ProductOfferReadResponse>> {
    private final ProductId productId;
}
