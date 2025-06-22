package com.e.bambi.inventory.application.productstatus.port.outbound.repository;

import com.e.bambi.inventory.application.productstatus.dto.response.ProductStatusResponse;
import com.e.bambi.inventory.domain.productstatus.valueobject.ProductStatusId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductStatusQueryRepository {
    Flux<ProductStatusResponse> findAll();

    Mono<Boolean> existsById(ProductStatusId productStatusId);
}
