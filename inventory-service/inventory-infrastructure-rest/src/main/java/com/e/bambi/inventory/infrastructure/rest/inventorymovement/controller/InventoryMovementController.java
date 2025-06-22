package com.e.bambi.inventory.infrastructure.rest.inventorymovement.controller;

import com.e.bambi.inventory.application.inventorymovement.dto.command.DeleteInventoryMovementCommand;
import com.e.bambi.inventory.application.inventorymovement.dto.query.InventoryMovementByIdQuery;
import com.e.bambi.inventory.application.inventorymovement.dto.response.InventoryMovementResponse;
import com.e.bambi.inventory.application.inventorymovement.dto.response.InventoryMovementSummaryReadResponse;
import com.e.bambi.inventory.application.shared.dto.response.PaginatedResultResponse;
import com.e.bambi.inventory.domain.inventorymovement.valueobject.InventoryMovementId;
import com.e.bambi.inventory.infrastructure.rest.inventorymovement.dto.request.CreateInventoryMovementRequestDto;
import com.e.bambi.inventory.infrastructure.rest.inventorymovement.dto.request.InventoryMovementRequestDto;
import com.e.bambi.inventory.infrastructure.rest.inventorymovement.dto.request.UpdateInventoryMovementRequestDto;
import com.e.bambi.inventory.infrastructure.rest.inventorymovement.mapper.InventoryMovementRestMapper;
import com.e.bambi.shared.kernel.application.port.inbound.bus.CommandBus;
import com.e.bambi.shared.kernel.application.port.inbound.bus.QueryBus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/inventory-movements")
public class InventoryMovementController {

    private final QueryBus queryBus;
    private final CommandBus commandBus;
    private final InventoryMovementRestMapper inventoryMovementRestMapper;

    @GetMapping
    public Mono<ResponseEntity<PaginatedResultResponse<InventoryMovementSummaryReadResponse>>> findInventoryMovements(
            @Valid
            InventoryMovementRequestDto filters) {
        return queryBus.dispatch(inventoryMovementRestMapper.toInventoryMovementQuery(filters))
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{inventoryMovementId}")
    public Mono<ResponseEntity<InventoryMovementSummaryReadResponse>> findInventoryMovement(@PathVariable @UUID
                                                                                            String inventoryMovementId) {
        return queryBus.dispatch(new InventoryMovementByIdQuery(
                new InventoryMovementId(java.util.UUID.fromString(inventoryMovementId))
        )).map(ResponseEntity::ok);
    }

    @PostMapping
    public Mono<ResponseEntity<InventoryMovementResponse>> saveInventoryMovement(@RequestBody @Valid
                                                                                 CreateInventoryMovementRequestDto
                                                                                         createInventoryMovementRequestDto) {
        return commandBus.dispatch(inventoryMovementRestMapper
                        .toCreateInventoryMovementCommand(createInventoryMovementRequestDto))
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{inventoryMovementId}")
    public Mono<ResponseEntity<InventoryMovementResponse>> updateInventoryMovement(@PathVariable @UUID
                                                                                   String inventoryMovementId,
                                                                                   @RequestBody @Valid
                                                                                   UpdateInventoryMovementRequestDto
                                                                                           updateInventoryMovementRequestDto) {
        return commandBus.dispatch(inventoryMovementRestMapper
                        .toUpdateInventoryMovementCommand(inventoryMovementId, updateInventoryMovementRequestDto))
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{inventoryMovementId}")
    public Mono<ResponseEntity<Void>> deleteInventoryMovement(@PathVariable @UUID String inventoryMovementId) {
        return commandBus.dispatch(new DeleteInventoryMovementCommand(
                new InventoryMovementId(java.util.UUID.fromString(inventoryMovementId))
        )).thenReturn(ResponseEntity.noContent().build());
    }
}
