package com.e.bambi.inventory.service.repository;

import com.e.bambi.inventory.service.model.Product;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ProductCrudRepository extends ReactiveCrudRepository<Product, UUID> {

    @Query("UPDATE products " +
            "SET product_status_id = 'ba0a6601-dfe4-485a-83f9-c149db939f2e' " + // inactive status
            "WHERE id = :productId")
    Mono<Void> softDeleteProduct(UUID productId);
}