package com.e.bambi.inventory.service.repository;

import com.e.bambi.inventory.service.model.Image;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface ImageCrudRepository extends ReactiveCrudRepository<Image, UUID> {
    Flux<Image> findByProductId(UUID productId);
}
