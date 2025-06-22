package com.e.bambi.inventory.application.offer.dto.query;

import com.e.bambi.inventory.application.shared.dto.response.PaginatedResultResponse;
import com.e.bambi.inventory.application.offer.dto.response.SupplierOfferReadResponse;
import com.e.bambi.shared.kernel.domain.valueobject.SupplierId;
import com.e.bambi.shared.kernel.application.bus.Query;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Getter
@RequiredArgsConstructor
public class OfferBySupplierIdQuery extends Query<Mono<PaginatedResultResponse<SupplierOfferReadResponse>>> {
    private final SupplierId supplierId;
    private final int size;
    private final int page;
}
