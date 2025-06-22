package com.e.bambi.inventory.infrastructure.persistence.product.repository.r2dbc;

import com.e.bambi.inventory.infrastructure.persistence.product.entity.ProductEntity;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface ProductR2dbcRepository extends R2dbcRepository<ProductEntity, UUID> {

    @Modifying
    @Query("DELETE FROM products WHERE id = :productId")
    Mono<Integer> deleteProductById(UUID productId);
}
