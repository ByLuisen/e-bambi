package com.e.bambi.inventory.application.offer.port.outbound.repository;

import com.e.bambi.inventory.application.offer.dto.response.ProductOfferReadResponse;
import com.e.bambi.inventory.application.offer.dto.response.SupplierOfferReadResponse;
import com.e.bambi.inventory.application.shared.dto.response.PaginatedResultResponse;
import com.e.bambi.inventory.domain.shared.valueobject.Stock;
import com.e.bambi.shared.kernel.domain.valueobject.SupplierId;
import com.e.bambi.shared.kernel.domain.valueobject.ProductId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OfferQueryRepository {
    Mono<PaginatedResultResponse<SupplierOfferReadResponse>> findBySupplierId(SupplierId supplierId, int size, int page);

    Flux<ProductOfferReadResponse> findByProductId(ProductId productId);

    Mono<Stock> findOfferStock(SupplierId supplierId, ProductId productId);
}
