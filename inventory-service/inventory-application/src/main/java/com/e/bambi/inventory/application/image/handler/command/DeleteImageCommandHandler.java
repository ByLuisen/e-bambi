package com.e.bambi.inventory.application.image.handler.command;

import com.e.bambi.inventory.application.image.dto.command.DeleteImageCommand;
import com.e.bambi.inventory.application.image.port.outbound.repository.ImageRepository;
import com.e.bambi.inventory.domain.exception.ImageNotFoundException;
import com.e.bambi.shared.kernel.application.bus.CommandHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeleteImageCommandHandler implements CommandHandler<Mono<Void>, DeleteImageCommand> {

    private final ImageRepository imageRepository;

    @Override
    public Mono<Void> handle(DeleteImageCommand command) {
        UUID imageId = command.getImageId().getValue();

        return imageRepository.deleteById(command.getImageId())
                .handle((updatedRows, sink) -> {
                    if (updatedRows < 1) {
                        log.error("Image with id: {} could not be deleted", imageId);
                        sink.error(new ImageNotFoundException("Image with id: " + imageId + " could not be found"));
                    } else {
                        sink.complete();
                    }
                });
    }
}
