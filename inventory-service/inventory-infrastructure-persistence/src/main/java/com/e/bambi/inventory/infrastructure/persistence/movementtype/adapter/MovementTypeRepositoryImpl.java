package com.e.bambi.inventory.infrastructure.persistence.movementtype.adapter;

import com.e.bambi.inventory.application.movementtype.port.outbond.repository.MovementTypeRepository;
import com.e.bambi.inventory.domain.movementtype.entity.MovementType;
import com.e.bambi.inventory.domain.shared.valueobject.MovementTypeId;
import com.e.bambi.inventory.infrastructure.persistence.movementtype.mapper.MovementTypePersistenceMapper;
import com.e.bambi.inventory.infrastructure.persistence.movementtype.repository.MovementTypeR2dbcEntityTemplate;
import com.e.bambi.inventory.infrastructure.persistence.movementtype.repository.MovementTypeR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class MovementTypeRepositoryImpl implements MovementTypeRepository {

    private final MovementTypeR2dbcRepository movementTypeR2dbcRepository;
    private final MovementTypeR2dbcEntityTemplate movementTypeR2dbcEntityTemplate;
    private final MovementTypePersistenceMapper movementTypePersistenceMapper;

    @Override
    public Mono<MovementType> insert(MovementType movementType) {
        return movementTypeR2dbcEntityTemplate
                .insert(movementTypePersistenceMapper.toMovementTypeEntity(movementType))
                .map(movementTypePersistenceMapper::toMovementType);
    }

    @Override
    public Mono<MovementType> update(MovementType movementType) {
        return movementTypeR2dbcRepository
                .save(movementTypePersistenceMapper.toMovementTypeEntity(movementType))
                .map(movementTypePersistenceMapper::toMovementType);
    }

    @Override
    public Mono<Integer> deleteById(MovementTypeId movementTypeId) {
        return movementTypeR2dbcRepository
                .deleteMovementTypeById(movementTypeId.getValue());
    }
}
