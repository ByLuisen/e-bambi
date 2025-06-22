package com.e.bambi.inventory.application.image.handler.query;

import com.e.bambi.inventory.application.image.dto.query.ImageByProductIdQuery;
import com.e.bambi.inventory.application.image.dto.response.ImageResponse;
import com.e.bambi.inventory.application.image.mapper.ImageApplicationMapper;
import com.e.bambi.inventory.application.image.port.outbound.repository.ImageQueryRepository;
import com.e.bambi.inventory.domain.exception.ImageNotFoundException;
import com.e.bambi.shared.kernel.application.bus.QueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ImageByProductIdQueryHandler implements QueryHandler<Flux<ImageResponse>, ImageByProductIdQuery> {

    private final ImageQueryRepository imageQueryRepository;
    private final ImageApplicationMapper imageApplicationMapper;

    @Override
    @Transactional(readOnly = true)
    public Flux<ImageResponse> handle(ImageByProductIdQuery query) {
        return imageQueryRepository.findByProductId(query.getProductId())
                .switchIfEmpty(Mono.error(new ImageNotFoundException("Images for the product with id: " +
                        query.getProductId().getValue() + " could not be found")));
    }
}
