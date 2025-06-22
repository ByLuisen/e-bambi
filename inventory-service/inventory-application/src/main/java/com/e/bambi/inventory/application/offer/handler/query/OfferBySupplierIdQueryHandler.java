package com.e.bambi.inventory.application.offer.handler.query;

import com.e.bambi.inventory.application.offer.dto.query.OfferBySupplierIdQuery;
import com.e.bambi.inventory.application.offer.dto.response.SupplierOfferReadResponse;
import com.e.bambi.inventory.application.offer.port.outbound.repository.OfferQueryRepository;
import com.e.bambi.inventory.application.shared.dto.response.PaginatedResultResponse;
import com.e.bambi.shared.kernel.application.bus.QueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class OfferBySupplierIdQueryHandler implements
        QueryHandler<Mono<PaginatedResultResponse<SupplierOfferReadResponse>>, OfferBySupplierIdQuery> {

    private final OfferQueryRepository offerQueryRepository;

    @Override
    @Transactional(readOnly = true)
    public Mono<PaginatedResultResponse<SupplierOfferReadResponse>> handle(OfferBySupplierIdQuery query) {
        return offerQueryRepository
                .findBySupplierId(query.getSupplierId(), query.getSize(), query.getPage());
    }
}
