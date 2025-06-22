package com.e.bambi.inventory.application.productstatus.handler.query;

import com.e.bambi.inventory.application.productstatus.dto.query.ProductStatusFindAllQuery;
import com.e.bambi.inventory.application.productstatus.dto.response.ProductStatusResponse;
import com.e.bambi.inventory.application.productstatus.port.outbound.repository.ProductStatusQueryRepository;
import com.e.bambi.inventory.domain.exception.ProductStatusNotFoundException;
import com.e.bambi.shared.kernel.application.bus.QueryHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductStatusFindAllQueryHandler implements
        QueryHandler<Flux<ProductStatusResponse>, ProductStatusFindAllQuery> {

    private final ProductStatusQueryRepository productStatusQueryRepository;

    @Override
    @Transactional(readOnly = true)
    public Flux<ProductStatusResponse> handle(ProductStatusFindAllQuery query) {
        return productStatusQueryRepository.findAll()
                .switchIfEmpty(
                        Mono.error(new ProductStatusNotFoundException("Product status could not be found"))
                );
    }
}
