package com.e.bambi.inventory.service.controller;

import com.e.bambi.inventory.service.model.Image;
import com.e.bambi.inventory.service.dto.ImageInputDTO;
import com.e.bambi.inventory.service.service.ImageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/images")
public class ImageController {

    private final ImageService imageService;

    @GetMapping("/{productId}")
    public Flux<Image> findImagesByProductId(@PathVariable @UUID String productId) {
        return imageService.findImagesByProductId(java.util.UUID.fromString(productId));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ResponseEntity<Object>> saveImage(@RequestBody @Valid ImageInputDTO imageInputDTO) {
        return imageService.saveImage(imageInputDTO);
    }

    @DeleteMapping("/{imageId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ResponseEntity<Object>> deleteImageById(@PathVariable @UUID String imageId) {
        return imageService.deleteImageById(java.util.UUID.fromString(imageId));
    }
}
