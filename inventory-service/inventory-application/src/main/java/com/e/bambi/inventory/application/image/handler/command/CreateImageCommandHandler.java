package com.e.bambi.inventory.application.image.handler.command;

import com.e.bambi.inventory.application.image.dto.command.CreateImageCommand;
import com.e.bambi.inventory.application.image.dto.response.ImageResponse;
import com.e.bambi.inventory.application.image.mapper.ImageApplicationMapper;
import com.e.bambi.inventory.application.image.port.outbound.repository.ImageRepository;
import com.e.bambi.inventory.domain.image.entity.Image;
import com.e.bambi.shared.kernel.application.bus.CommandHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateImageCommandHandler implements CommandHandler<Mono<ImageResponse>, CreateImageCommand> {

    private final ImageRepository imageRepository;
    private final ImageApplicationMapper imageApplicationMapper;

    @Override
    public Mono<ImageResponse> handle(CreateImageCommand command) {
        Image image = imageApplicationMapper.toImage(command);
        image.initializeImage();
        return imageRepository.insert(image)
                .onErrorMap(DuplicateKeyException.class, e -> {
                    log.error("Image with url: {} already exists", command.getImageUrl());
                    return new DuplicateKeyException("Image with url: " + command.getImageUrl() + " already exists");
                })
                .onErrorMap(Exception.class, e -> new RuntimeException(e.getMessage(), e.getCause()))
                .map(imageApplicationMapper::toImageResponse);
    }
}
