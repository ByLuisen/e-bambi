package com.e.bambi.inventory.infrastructure.persistence.productstatus.repository;

import com.e.bambi.inventory.application.productstatus.dto.response.ProductStatusResponse;
import com.e.bambi.inventory.infrastructure.persistence.productstatus.entity.ProductStatusEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface ProductStatusR2dbcRepository extends R2dbcRepository<ProductStatusEntity, UUID> {

    @Query("SELECT id, name FROM product_statuses")
    Flux<ProductStatusResponse> productStatusFindAll();
}
