package com.e.bambi.inventory.application.brand.port.outbound.repository;

import com.e.bambi.inventory.application.brand.dto.response.BrandResponse;
import com.e.bambi.inventory.domain.brand.entity.Brand;
import com.e.bambi.inventory.domain.shared.valueobject.BrandId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BrandQueryRepository {

    Flux<BrandResponse> findAll();

    Mono<Boolean> existsById(BrandId brandId);

}
