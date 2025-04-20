package com.e.bambi.inventory.service.service;

import com.e.bambi.inventory.service.model.Image;
import com.e.bambi.inventory.service.dto.ImageInputDTO;
import com.e.bambi.inventory.service.exception.ImageNotFoundException;
import com.e.bambi.inventory.service.mapper.ImageMapper;
import com.e.bambi.inventory.service.repository.ImageCrudRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageMapper imageMapper;
    private final ImageCrudRepository imageCrudRepository;

    public Flux<Image> findImagesByProductId(UUID productId) {
        return imageCrudRepository.findByProductId(productId)
                .switchIfEmpty(Flux.error(new ImageNotFoundException("Image not found.")));
    }

    public Mono<ResponseEntity<Object>> saveImage(ImageInputDTO imageInputDTO) {
        Image image = imageMapper.imageInputDTOToImage(imageInputDTO);

        return imageCrudRepository.save(image)
                .map(savedImage -> {
                    String location = "/v1/images/" + savedImage.getId();

                    return ResponseEntity.created(URI.create(location)).build();
                })
                .onErrorResume(DuplicateKeyException.class, e -> {
                    return Mono.error(new DuplicateKeyException("Duplicate entry detected. Please ensure the data is unique."));
                });
    }

    public Mono<ResponseEntity<Object>> deleteImageById(UUID imageId) {
        return imageCrudRepository.existsById(imageId)
                .flatMap(imageExists -> {
                    if (imageExists) {
                        return imageCrudRepository.deleteById(imageId)
                                .thenReturn(ResponseEntity.noContent().build());
                    }
                    return Mono.error(new ImageNotFoundException("Image not found."));
                });
    }
}
