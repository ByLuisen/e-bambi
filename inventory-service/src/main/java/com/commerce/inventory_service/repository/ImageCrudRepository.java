package com.commerce.inventory_service.repository;

import com.commerce.inventory_service.domain.Image;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface ImageCrudRepository extends ReactiveCrudRepository<Image, UUID> {
    Flux<Image> findByProductId(UUID productId);
}
