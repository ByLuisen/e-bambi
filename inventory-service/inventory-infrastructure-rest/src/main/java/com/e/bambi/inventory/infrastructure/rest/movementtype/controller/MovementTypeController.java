package com.e.bambi.inventory.infrastructure.rest.movementtype.controller;

import com.e.bambi.inventory.application.movementtype.dto.command.DeleteMovementTypeCommand;
import com.e.bambi.inventory.application.movementtype.dto.query.MovementTypeFindAllQuery;
import com.e.bambi.inventory.application.movementtype.dto.response.MovementTypeResponse;
import com.e.bambi.inventory.domain.shared.valueobject.MovementTypeId;
import com.e.bambi.inventory.infrastructure.rest.movementtype.dto.request.CreateMovementTypeRequestDto;
import com.e.bambi.inventory.infrastructure.rest.movementtype.dto.request.UpdateMovementTypeRequestDto;
import com.e.bambi.inventory.infrastructure.rest.movementtype.mapper.MovementTypeRestMapper;
import com.e.bambi.shared.kernel.application.port.inbound.bus.CommandBus;
import com.e.bambi.shared.kernel.application.port.inbound.bus.QueryBus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/movement-types")
public class MovementTypeController {

    private final QueryBus queryBus;
    private final CommandBus commandBus;
    private final MovementTypeRestMapper movementTypeRestMapper;

    @GetMapping
    public Flux<MovementTypeResponse> findAllMovementTypes() {
        return queryBus.dispatch(new MovementTypeFindAllQuery());
    }

    @PostMapping
    public Mono<ResponseEntity<MovementTypeResponse>> saveMovementType(@RequestBody @Valid
                                                                       CreateMovementTypeRequestDto
                                                                               createMovementTypeRequestDto) {
        return commandBus.dispatch(movementTypeRestMapper
                .toCreateMovementTypeCommand(createMovementTypeRequestDto)
        ).map(ResponseEntity::ok);
    }

    @PutMapping("/{movementTypeId}")
    public Mono<ResponseEntity<MovementTypeResponse>> updateMovementType(@PathVariable @UUID
                                                           String movementTypeId,
                                                           @RequestBody @Valid
                                                           UpdateMovementTypeRequestDto updateMovementTypeRequestDto) {
        return commandBus.dispatch(movementTypeRestMapper
                .toUpdateMovementTypeCommand(movementTypeId, updateMovementTypeRequestDto)
        ).map(ResponseEntity::ok);
    }

    @DeleteMapping("/{movementTypeId}")
    public Mono<ResponseEntity<Void>> deleteMovementTypeById(@PathVariable @UUID String movementTypeId) {
        return commandBus.dispatch(new DeleteMovementTypeCommand(
                new MovementTypeId(java.util.UUID.fromString(movementTypeId))
        )).thenReturn(ResponseEntity.noContent().build());
    }
}
