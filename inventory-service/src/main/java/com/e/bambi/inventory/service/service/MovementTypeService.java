package com.e.bambi.inventory.service.service;

import com.e.bambi.inventory.service.model.MovementType;
import com.e.bambi.inventory.service.dto.MovementTypeInputDTO;
import com.e.bambi.inventory.service.exception.MovementTypeNotFoundException;
import com.e.bambi.inventory.service.mapper.MovementTypeMapper;
import com.e.bambi.inventory.service.repository.MovementTypeCrudRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MovementTypeService {

    private final MovementTypeMapper movementTypeMapper;
    private final MovementTypeCrudRepository movementTypeCrudRepository;

    public Flux<MovementType> findAllMovementTypes() {
        return movementTypeCrudRepository.findAll()
                .switchIfEmpty(Flux.error(new MovementTypeNotFoundException("No Movement types recorded.")));
    }

    public Mono<ResponseEntity<Object>> saveMovementType(MovementTypeInputDTO movementTypeInputDTO) {
        MovementType movementType = movementTypeMapper.movementTypeInputDTOToMovementType(movementTypeInputDTO);

        return movementTypeCrudRepository.save(movementType)
                .map(savedMovementType -> {
                    String location = "/v1/movement-types/" + savedMovementType.getId();

                    return ResponseEntity.created(URI.create(location)).build();
                })
                .onErrorResume(DuplicateKeyException.class, e -> {
                    return Mono.error(new DuplicateKeyException("Duplicate entry detected. Please ensure the data is unique."));
                });
    }

    public Mono<ResponseEntity<Object>> updateMovementType(UUID movementTypeId, MovementTypeInputDTO movementTypeInputDTO) {
        MovementType movementType = movementTypeMapper.movementTypeInputDTOToMovementType(movementTypeInputDTO);

        return movementTypeCrudRepository.findById(movementTypeId)
                .flatMap(movementTypeObtained -> {
                    movementType.setId(movementTypeId);
                    if (!movementTypeObtained.equals(movementType)) {
                        return movementTypeCrudRepository.save(movementType)
                                .thenReturn(ResponseEntity.noContent().build())
                                .onErrorResume(DuplicateKeyException.class, e -> {
                                    return Mono.error(new DuplicateKeyException("Duplicate entry detected. Please ensure the data is unique."));
                                });
                    }
                    return Mono.just(ResponseEntity.noContent().build());
                })
                .switchIfEmpty(Mono.error(new MovementTypeNotFoundException("Movement type not found.")));
    }

    public Mono<ResponseEntity<Object>> deleteMovementTypeById(UUID movementTypeId) {
        return movementTypeCrudRepository.existsById(movementTypeId)
                .flatMap(movementTypeExists -> {
                    if (movementTypeExists) {
                        return movementTypeCrudRepository.deleteById(movementTypeId)
                                .thenReturn(ResponseEntity.noContent().build());
                    }
                    return Mono.error(new MovementTypeNotFoundException("Movement type not found."));
                });
    }
}
