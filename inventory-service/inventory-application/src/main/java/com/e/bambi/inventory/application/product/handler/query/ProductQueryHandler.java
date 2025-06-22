package com.e.bambi.inventory.application.product.handler.query;

import com.e.bambi.inventory.application.product.dto.query.ProductQuery;
import com.e.bambi.inventory.application.product.dto.response.ProductSummaryReadResponse;
import com.e.bambi.inventory.application.product.port.outbond.repository.ProductQueryRepository;
import com.e.bambi.inventory.application.shared.dto.response.PaginatedResultResponse;
import com.e.bambi.inventory.domain.exception.ProductNotFoundException;
import com.e.bambi.shared.kernel.application.bus.QueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductQueryHandler implements
        QueryHandler<Mono<PaginatedResultResponse<ProductSummaryReadResponse>>, ProductQuery> {

    private final ProductQueryRepository productQueryRepository;

    @Override
    @Transactional(readOnly = true)
    public Mono<PaginatedResultResponse<ProductSummaryReadResponse>> handle(ProductQuery query) {
        return productQueryRepository.findAll(query)
                .switchIfEmpty(
                        Mono.error(new ProductNotFoundException("Products could not be found"))
                );
    }
}
