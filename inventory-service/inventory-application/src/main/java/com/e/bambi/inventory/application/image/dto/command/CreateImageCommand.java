package com.e.bambi.inventory.application.image.dto.command;

import com.e.bambi.inventory.application.image.dto.response.ImageResponse;
import com.e.bambi.shared.kernel.application.bus.Command;
import com.e.bambi.shared.kernel.domain.valueobject.ProductId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Getter
@RequiredArgsConstructor
public class CreateImageCommand extends Command<Mono<ImageResponse>> {
    private final ProductId productId;
    private final String imageUrl;
}
