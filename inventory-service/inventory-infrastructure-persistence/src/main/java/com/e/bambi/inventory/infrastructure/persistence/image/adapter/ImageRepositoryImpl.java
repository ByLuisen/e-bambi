package com.e.bambi.inventory.infrastructure.persistence.image.adapter;

import com.e.bambi.inventory.application.image.port.outbound.repository.ImageRepository;
import com.e.bambi.inventory.domain.image.entity.Image;
import com.e.bambi.inventory.domain.image.valueobject.ImageId;
import com.e.bambi.inventory.infrastructure.persistence.image.mapper.ImagePersistenceMapper;
import com.e.bambi.inventory.infrastructure.persistence.image.repository.ImageR2dbcEntityTemplate;
import com.e.bambi.inventory.infrastructure.persistence.image.repository.ImageR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ImageRepositoryImpl implements ImageRepository {

    private final ImageR2dbcRepository imageR2dbcRepository;
    private final ImageR2dbcEntityTemplate imageR2dbcEntityTemplate;
    private final ImagePersistenceMapper imagePersistenceMapper;

    @Override
    public Mono<Image> insert(Image image) {
        return imageR2dbcEntityTemplate
                .insert(imagePersistenceMapper.toImageEntity(image))
                .map(imagePersistenceMapper::toImage);
    }

    @Override
    public Mono<Integer> deleteById(ImageId imageId) {
        return imageR2dbcRepository.deleteImageById(imageId.getValue());
    }
}
