package com.e.bambi.inventory.infrastructure.persistence.productstatus.adapter;

import com.e.bambi.inventory.application.productstatus.dto.response.ProductStatusResponse;
import com.e.bambi.inventory.application.productstatus.port.outbound.repository.ProductStatusQueryRepository;
import com.e.bambi.inventory.domain.productstatus.valueobject.ProductStatusId;
import com.e.bambi.inventory.infrastructure.persistence.productstatus.repository.ProductStatusR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductStatusQueryRepositoryImpl implements ProductStatusQueryRepository {

    private final ProductStatusR2dbcRepository productStatusR2dbcRepository;

    @Override
    public Flux<ProductStatusResponse> findAll() {
        return productStatusR2dbcRepository
                .productStatusFindAll();
    }

    @Override
    public Mono<Boolean> existsById(ProductStatusId productStatusId) {
        return productStatusR2dbcRepository
                .existsById(productStatusId.getValue());
    }
}
