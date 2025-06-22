package com.e.bambi.inventory.infrastructure.persistence.image.adapter;

import com.e.bambi.inventory.application.image.dto.response.ImageResponse;
import com.e.bambi.inventory.application.image.port.outbound.repository.ImageQueryRepository;
import com.e.bambi.inventory.infrastructure.persistence.image.repository.ImageR2dbcRepository;
import com.e.bambi.shared.kernel.domain.valueobject.ProductId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
public class ImageQueryRepositoryImpl implements ImageQueryRepository {

    private final ImageR2dbcRepository imageR2dbcRepository;

    @Override
    public Flux<ImageResponse> findByProductId(ProductId productId) {
        return imageR2dbcRepository
                .imageFindByProductId(productId.getValue());
    }
}
