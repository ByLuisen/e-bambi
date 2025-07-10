package com.e.bambi.inventory.infrastructure.rest.image.controller;

import com.e.bambi.inventory.application.image.dto.command.CreateImageCommand;
import com.e.bambi.inventory.application.image.dto.command.DeleteImageCommand;
import com.e.bambi.inventory.application.image.dto.query.ImageByProductIdQuery;
import com.e.bambi.inventory.application.image.dto.response.ImageResponse;
import com.e.bambi.inventory.domain.image.valueobject.ImageId;
import com.e.bambi.inventory.infrastructure.rest.image.dto.request.CreateImageRequestDto;
import com.e.bambi.shared.kernel.application.port.inbound.bus.CommandBus;
import com.e.bambi.shared.kernel.application.port.inbound.bus.QueryBus;
import com.e.bambi.shared.kernel.domain.valueobject.ProductId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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

    private final CommandBus commandBus;
    private final QueryBus queryBus;

    @GetMapping
    public Flux<ImageResponse> getProductImages(@UUID @NotNull String productId) {
        return queryBus.dispatch(new ImageByProductIdQuery(
                new ProductId(java.util.UUID.fromString(productId))
        ));
    }

    @PostMapping
    public Mono<ResponseEntity<ImageResponse>> saveImage(@RequestBody @Valid CreateImageRequestDto createImageRequestDto) {
        return commandBus.dispatch(new CreateImageCommand(
                new ProductId(java.util.UUID.fromString(createImageRequestDto.getProductId())),
                createImageRequestDto.getImageUrl()
        )).map(ResponseEntity::ok);
    }

    @DeleteMapping("/{imageId}")
    public Mono<ResponseEntity<Void>> deleteImageById(@PathVariable @UUID String imageId) {
        return commandBus.dispatch(new DeleteImageCommand(
                new ImageId(java.util.UUID.fromString(imageId))
        )).thenReturn(ResponseEntity.noContent().build());
    }
}
