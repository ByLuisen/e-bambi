package com.e.bambi.inventory.application.movementtype.handler.query;

import com.e.bambi.inventory.application.movementtype.dto.query.MovementTypeFindAllQuery;
import com.e.bambi.inventory.application.movementtype.dto.response.MovementTypeResponse;
import com.e.bambi.inventory.application.movementtype.port.outbond.repository.MovementTypeQueryRepository;
import com.e.bambi.inventory.domain.exception.MovementTypeNotFoundException;
import com.e.bambi.shared.kernel.application.bus.QueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
public class MovementTypeFindAllQueryHandler implements QueryHandler<Flux<MovementTypeResponse>, MovementTypeFindAllQuery> {

    private final MovementTypeQueryRepository movementTypeQueryRepository;

    @Override
    @Transactional(readOnly = true)
    public Flux<MovementTypeResponse> handle(MovementTypeFindAllQuery query) {
        return movementTypeQueryRepository.findAll()
                .switchIfEmpty(
                        Flux.error(new MovementTypeNotFoundException("No movement types could be found"))
                );
    }
}
