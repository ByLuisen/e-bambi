package com.e.bambi.inventory.infrastructure.persistence.image.repository;

import com.e.bambi.inventory.application.image.dto.response.ImageResponse;
import com.e.bambi.inventory.infrastructure.persistence.image.entity.ImageEntity;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface ImageR2dbcRepository extends R2dbcRepository<ImageEntity, UUID> {

    @Modifying
    @Query("DELETE FROM images WHERE id = :imageId")
    Mono<Integer> deleteImageById(UUID imageId);

    @Query("SELECT id, image_url FROM images WHERE product_id = :productId")
    Flux<ImageResponse> imageFindByProductId(UUID productId);
}
