package com.e.bambi.inventory.application.product.handler.query;

import com.e.bambi.inventory.application.product.dto.query.ProductByIdQuery;
import com.e.bambi.inventory.application.product.dto.response.ProductWithDetailsReadResponse;
import com.e.bambi.inventory.application.product.port.outbond.repository.ProductQueryRepository;
import com.e.bambi.inventory.domain.exception.ProductNotFoundException;
import com.e.bambi.shared.kernel.application.bus.QueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductByIdQueryHandler implements QueryHandler<Mono<ProductWithDetailsReadResponse>, ProductByIdQuery> {

    private final ProductQueryRepository productQueryRepository;

    @Override
    @Transactional(readOnly = true)
    public Mono<ProductWithDetailsReadResponse> handle(ProductByIdQuery query) {
        return productQueryRepository.findById(query.getProductId())
                .switchIfEmpty(Mono.error(new ProductNotFoundException("Product with id: " +
                        query.getProductId().getValue() + " doesn't exists")));
    }
}
