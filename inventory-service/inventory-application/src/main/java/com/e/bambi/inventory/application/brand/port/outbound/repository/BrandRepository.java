package com.e.bambi.inventory.application.brand.port.outbound.repository;

import com.e.bambi.inventory.domain.brand.entity.Brand;
import com.e.bambi.inventory.domain.shared.valueobject.BrandId;
import reactor.core.publisher.Mono;

public interface BrandRepository {

    Mono<Brand> insert(Brand brand);

    Mono<Brand> update(Brand brand);

    Mono<Integer> deleteById(BrandId brandId);

}
