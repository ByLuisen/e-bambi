package com.e.bambi.inventory.application.movementtype.port.outbond.repository;

import com.e.bambi.inventory.domain.movementtype.entity.MovementType;
import com.e.bambi.inventory.domain.shared.valueobject.MovementTypeId;
import reactor.core.publisher.Mono;

public interface MovementTypeRepository {

    Mono<MovementType> insert(MovementType movementType);

    Mono<MovementType> update(MovementType movementType);

    Mono<Integer> deleteById(MovementTypeId movementTypeId);

}
