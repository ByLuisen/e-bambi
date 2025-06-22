package com.e.bambi.inventory.infrastructure.persistence.brand.adapter;

import com.e.bambi.inventory.application.brand.port.outbound.repository.BrandRepository;
import com.e.bambi.inventory.domain.brand.entity.Brand;
import com.e.bambi.inventory.domain.shared.valueobject.BrandId;
import com.e.bambi.inventory.infrastructure.persistence.brand.mapper.BrandPersistenceMapper;
import com.e.bambi.inventory.infrastructure.persistence.brand.repository.BrandR2dbcEntityTemplate;
import com.e.bambi.inventory.infrastructure.persistence.brand.repository.BrandR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class BrandRepositoryImpl implements BrandRepository {

    private final BrandR2dbcRepository brandR2dbcRepository;
    private final BrandR2dbcEntityTemplate brandR2dbcEntityTemplate;
    private final BrandPersistenceMapper brandPersistenceMapper;

    @Override
    public Mono<Brand> insert(Brand brand) {
        return brandR2dbcEntityTemplate
                .insert(brandPersistenceMapper.toBrandEntity(brand))
                .map(brandPersistenceMapper::toBrand);
    }

    @Override
    public Mono<Brand> update(Brand brand) {
        return brandR2dbcRepository
                .save(brandPersistenceMapper.toBrandEntity(brand))
                .map(brandPersistenceMapper::toBrand);
    }

    @Override
    public Mono<Integer> deleteById(BrandId brandId) {
        return brandR2dbcRepository.deleteBrandById(brandId.getValue());
    }

}
