package com.e.bambi.inventory.application.image.port.outbound.repository;

import com.e.bambi.inventory.domain.image.entity.Image;
import com.e.bambi.inventory.domain.image.valueobject.ImageId;
import reactor.core.publisher.Mono;

public interface ImageRepository {
    Mono<Image> insert(Image image);
    Mono<Integer> deleteById(ImageId imageId);
}
