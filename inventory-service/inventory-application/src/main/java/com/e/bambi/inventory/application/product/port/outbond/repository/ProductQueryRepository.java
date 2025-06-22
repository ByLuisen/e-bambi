package com.e.bambi.inventory.application.product.port.outbond.repository;

import com.e.bambi.inventory.application.product.dto.query.ProductQuery;
import com.e.bambi.inventory.application.product.dto.response.ProductSummaryReadResponse;
import com.e.bambi.inventory.application.product.dto.response.ProductWithDetailsReadResponse;
import com.e.bambi.inventory.application.shared.dto.response.PaginatedResultResponse;
import com.e.bambi.shared.kernel.domain.valueobject.ProductId;
import reactor.core.publisher.Mono;

public interface ProductQueryRepository {
    Mono<PaginatedResultResponse<ProductSummaryReadResponse>> findAll(ProductQuery query);

    Mono<ProductWithDetailsReadResponse> findById(ProductId productId);

    Mono<Boolean> existsById(ProductId productId);

}
