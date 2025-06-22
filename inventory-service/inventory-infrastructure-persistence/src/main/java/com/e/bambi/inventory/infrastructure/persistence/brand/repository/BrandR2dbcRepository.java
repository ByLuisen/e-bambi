package com.e.bambi.inventory.infrastructure.persistence.brand.repository;

import com.e.bambi.inventory.application.brand.dto.response.BrandResponse;
import com.e.bambi.inventory.infrastructure.persistence.brand.entity.BrandEntity;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface BrandR2dbcRepository extends R2dbcRepository<BrandEntity, UUID> {

    @Modifying
    @Query("DELETE FROM brands WHERE id = :brandId")
    Mono<Integer> deleteBrandById(UUID brandId);

    @Query("SELECT id, name FROM brands")
    Flux<BrandResponse> brandFindAll();
}
