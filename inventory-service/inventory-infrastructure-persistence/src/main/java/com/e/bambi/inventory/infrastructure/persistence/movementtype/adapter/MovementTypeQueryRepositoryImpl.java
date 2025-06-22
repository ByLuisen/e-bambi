package com.e.bambi.inventory.infrastructure.persistence.movementtype.adapter;

import com.e.bambi.inventory.application.movementtype.dto.response.MovementTypeResponse;
import com.e.bambi.inventory.application.movementtype.port.outbond.repository.MovementTypeQueryRepository;
import com.e.bambi.inventory.domain.shared.valueobject.MovementTypeId;
import com.e.bambi.inventory.infrastructure.persistence.movementtype.repository.MovementTypeR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class MovementTypeQueryRepositoryImpl implements MovementTypeQueryRepository {

    private final MovementTypeR2dbcRepository movementTypeR2dbcRepository;


    @Override
    public Flux<MovementTypeResponse> findAll() {
        return movementTypeR2dbcRepository.movementTypeFindAll();
    }

    @Override
    public Mono<Boolean> existsById(MovementTypeId movementTypeId) {
        return movementTypeR2dbcRepository
                .existsById(movementTypeId.getValue());
    }
}
