package com.e.bambi.inventory.application.movementtype.port.outbond.repository;

import com.e.bambi.inventory.application.movementtype.dto.response.MovementTypeResponse;
import com.e.bambi.inventory.domain.shared.valueobject.MovementTypeId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovementTypeQueryRepository {
    Flux<MovementTypeResponse> findAll();

    Mono<Boolean> existsById(MovementTypeId movementTypeId);

}
