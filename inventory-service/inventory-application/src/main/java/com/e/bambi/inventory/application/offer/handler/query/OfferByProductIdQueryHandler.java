package com.e.bambi.inventory.application.offer.handler.query;

import com.e.bambi.inventory.application.offer.dto.query.OfferByProductIdQuery;
import com.e.bambi.inventory.application.offer.dto.response.ProductOfferReadResponse;
import com.e.bambi.inventory.application.offer.port.outbound.repository.OfferQueryRepository;
import com.e.bambi.inventory.domain.exception.ProductNotFoundException;
import com.e.bambi.shared.kernel.application.bus.QueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
public class OfferByProductIdQueryHandler implements QueryHandler<Flux<ProductOfferReadResponse>, OfferByProductIdQuery> {

    private final OfferQueryRepository offerQueryRepository;

    @Override
    @Transactional(readOnly = true)
    public Flux<ProductOfferReadResponse> handle(OfferByProductIdQuery query) {
        return offerQueryRepository.findByProductId(query.getProductId())
                .switchIfEmpty(
                        Flux.error(new ProductNotFoundException("Product with id: " +
                                query.getProductId().getValue() + " could not be found"))
                );
    }
}
