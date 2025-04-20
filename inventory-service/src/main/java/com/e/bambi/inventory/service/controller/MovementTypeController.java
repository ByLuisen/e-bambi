package com.e.bambi.inventory.service.controller;

import com.e.bambi.inventory.service.model.MovementType;
import com.e.bambi.inventory.service.dto.MovementTypeInputDTO;
import com.e.bambi.inventory.service.service.MovementTypeService;
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

    private final MovementTypeService movementTypeService;

    @GetMapping
    public Flux<MovementType> findAllMovementTypes() {
        return movementTypeService.findAllMovementTypes();
    }

    @PostMapping
    public Mono<ResponseEntity<Object>> saveMovementType(@RequestBody @Valid MovementTypeInputDTO movementTypeInputDTO) {
        return movementTypeService.saveMovementType(movementTypeInputDTO);
    }

    @PutMapping("/{movementTypeId}")
    public Mono<ResponseEntity<Object>> updateMovementType(@PathVariable @UUID String movementTypeId,
                                                           @RequestBody @Valid MovementTypeInputDTO movementTypeInputDTO) {
        return movementTypeService.updateMovementType(java.util.UUID.fromString(movementTypeId), movementTypeInputDTO);
    }

    @DeleteMapping("/{movementTypeId}")
    public Mono<ResponseEntity<Object>> deleteMovementTypeById(@PathVariable @UUID String movementTypeId) {
        return movementTypeService.deleteMovementTypeById(java.util.UUID.fromString(movementTypeId));
    }
}
