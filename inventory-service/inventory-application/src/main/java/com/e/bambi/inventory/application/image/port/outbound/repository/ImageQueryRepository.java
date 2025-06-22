package com.e.bambi.inventory.application.image.port.outbound.repository;

import com.e.bambi.inventory.application.image.dto.response.ImageResponse;
import com.e.bambi.inventory.domain.image.entity.Image;
import com.e.bambi.shared.kernel.domain.valueobject.ProductId;
import reactor.core.publisher.Flux;

public interface ImageQueryRepository {

    Flux<ImageResponse> findByProductId(ProductId productId);

}
