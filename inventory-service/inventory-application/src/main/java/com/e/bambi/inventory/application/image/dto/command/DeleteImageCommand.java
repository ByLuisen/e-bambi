package com.e.bambi.inventory.application.image.dto.command;

import com.e.bambi.inventory.domain.image.valueobject.ImageId;
import com.e.bambi.shared.kernel.application.bus.Command;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Getter
@RequiredArgsConstructor
public class DeleteImageCommand extends Command<Mono<Void>> {
    private final ImageId imageId;
}
