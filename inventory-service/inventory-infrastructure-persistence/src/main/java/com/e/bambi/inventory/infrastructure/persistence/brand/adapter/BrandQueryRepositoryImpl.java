package com.e.bambi.inventory.infrastructure.persistence.brand.adapter;

import com.e.bambi.inventory.application.brand.dto.response.BrandResponse;
import com.e.bambi.inventory.application.brand.port.outbound.repository.BrandQueryRepository;
import com.e.bambi.inventory.domain.shared.valueobject.BrandId;
import com.e.bambi.inventory.infrastructure.persistence.brand.repository.BrandR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class BrandQueryRepositoryImpl implements BrandQueryRepository {

    private final BrandR2dbcRepository brandR2dbcRepository;

    @Override
    public Flux<BrandResponse> findAll() {
        return brandR2dbcRepository.brandFindAll();
    }

    @Override
    public Mono<Boolean> existsById(BrandId brandId) {
        return brandR2dbcRepository
                .existsById(brandId.getValue());
    }
}
