package com.e.bambi.inventory.service.controller;

import com.e.bambi.inventory.service.model.InventoryMovement;
import com.e.bambi.inventory.service.dto.InventoryMovementFilterInputDTO;
import com.e.bambi.inventory.service.dto.InventoryMovementInputDTO;
import com.e.bambi.inventory.service.dto.PaginatedResultOutputDTO;
import com.e.bambi.inventory.service.service.InventoryMovementService;
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

    private final InventoryMovementService inventoryMovementService;

    @GetMapping
    public Mono<PaginatedResultOutputDTO> findInventoriesWithPaginationAndFilters(@Valid InventoryMovementFilterInputDTO filters) {
        return inventoryMovementService.findInventoriesWithPaginationAndFilters(filters);
    }

    @GetMapping("/{inventoryMovementId}")
    public Mono<ResponseEntity<InventoryMovement>> findInventoryMovementById(@PathVariable @UUID String inventoryMovementId) {
        return inventoryMovementService.findInventoryMovementById(java.util.UUID.fromString(inventoryMovementId));
    }

    @PostMapping
    public Mono<ResponseEntity<Object>> saveInventoryMovement(@RequestBody @Valid InventoryMovementInputDTO inventoryMovementInputDTO) {
        return inventoryMovementService.saveInventoryMovement(inventoryMovementInputDTO);
    }

    @PutMapping("/{inventoryMovementId}")
    public Mono<ResponseEntity<Object>> updateInventoryMovementById(
            @PathVariable @UUID String inventoryMovementId, @RequestBody @Valid InventoryMovementInputDTO inventoryMovementInputDTO) {
        return inventoryMovementService.updateInventoryMovementById(java.util.UUID.fromString(inventoryMovementId), inventoryMovementInputDTO);
    }

    @DeleteMapping("/{inventoryMovementId}")
    public Mono<ResponseEntity<Object>> deleteInventoryMovementById(@PathVariable @UUID String inventoryMovementId) {
        return inventoryMovementService.deleteInventoryMovementById(java.util.UUID.fromString(inventoryMovementId));
    }
}
