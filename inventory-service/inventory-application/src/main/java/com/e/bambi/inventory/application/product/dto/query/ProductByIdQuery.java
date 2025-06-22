package com.e.bambi.inventory.application.product.dto.query;

import com.e.bambi.inventory.application.product.dto.response.ProductWithDetailsReadResponse;
import com.e.bambi.shared.kernel.application.bus.Query;
import com.e.bambi.shared.kernel.domain.valueobject.ProductId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Getter
@RequiredArgsConstructor
public class ProductByIdQuery extends Query<Mono<ProductWithDetailsReadResponse>> {
    private final ProductId productId;
}
