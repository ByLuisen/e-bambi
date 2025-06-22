package com.e.bambi.inventory.application.image.dto.query;

import com.e.bambi.inventory.application.image.dto.response.ImageResponse;
import com.e.bambi.shared.kernel.application.bus.Query;
import com.e.bambi.shared.kernel.domain.valueobject.ProductId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@Getter
@RequiredArgsConstructor
public class ImageByProductIdQuery extends Query<Flux<ImageResponse>> {
    private final ProductId productId;
}
