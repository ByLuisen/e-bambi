package com.e.bambi.inventory.infrastructure.persistence.offer.adapter;

import com.e.bambi.inventory.application.offer.dto.response.ProductOfferReadResponse;
import com.e.bambi.inventory.application.offer.dto.response.SupplierOfferReadResponse;
import com.e.bambi.inventory.application.offer.port.outbound.repository.OfferQueryRepository;
import com.e.bambi.inventory.application.shared.dto.response.PaginatedResultResponse;
import com.e.bambi.inventory.domain.shared.valueobject.Stock;
import com.e.bambi.shared.kernel.domain.valueobject.SupplierId;
import com.e.bambi.inventory.infrastructure.persistence.offer.repository.jooq.OfferJooqRepository;
import com.e.bambi.shared.kernel.domain.valueobject.ProductId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class OfferQueryRepositoryImpl implements OfferQueryRepository {

    private final OfferJooqRepository offerJooqRepository;

    @Override
    public Mono<PaginatedResultResponse<SupplierOfferReadResponse>> findBySupplierId(SupplierId supplierId,
                                                                                     int size, int page) {
        return offerJooqRepository
                .findBySupplierId(supplierId.getValue(), size, page);
    }

    @Override
    public Flux<ProductOfferReadResponse> findByProductId(ProductId productId) {
        return offerJooqRepository.findByProductId(productId.getValue());
    }

    @Override
    public Mono<Stock> findOfferStock(SupplierId supplierId, ProductId productId) {
        return offerJooqRepository.findOfferStock(supplierId.getValue(), productId.getValue());
    }
}
