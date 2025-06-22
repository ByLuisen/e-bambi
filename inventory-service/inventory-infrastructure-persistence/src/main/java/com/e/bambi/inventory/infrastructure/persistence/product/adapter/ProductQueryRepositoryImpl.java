package com.e.bambi.inventory.infrastructure.persistence.product.adapter;

import com.e.bambi.inventory.application.product.dto.query.ProductQuery;
import com.e.bambi.inventory.application.product.dto.response.ProductSummaryReadResponse;
import com.e.bambi.inventory.application.product.dto.response.ProductWithDetailsReadResponse;
import com.e.bambi.inventory.application.product.port.outbond.repository.ProductQueryRepository;
import com.e.bambi.inventory.application.shared.dto.response.PaginatedResultResponse;
import com.e.bambi.inventory.infrastructure.persistence.product.repository.jooq.ProductJooqRepository;
import com.e.bambi.inventory.infrastructure.persistence.product.repository.r2dbc.ProductR2dbcRepository;
import com.e.bambi.shared.kernel.domain.valueobject.ProductId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductQueryRepositoryImpl implements ProductQueryRepository {

    private final ProductJooqRepository productJooqRepository;
    private final ProductR2dbcRepository productR2dbcRepository;

    @Override
    public Mono<PaginatedResultResponse<ProductSummaryReadResponse>> findAll(ProductQuery query) {
        return productJooqRepository.searchProductSummary(query);
    }

    @Override
    public Mono<ProductWithDetailsReadResponse> findById(ProductId productId) {
        return productJooqRepository.searchProductWithDetails(productId.getValue());
    }

    @Override
    public Mono<Boolean> existsById(ProductId productId) {
        return productR2dbcRepository
                .existsById(productId.getValue());
    }
}
