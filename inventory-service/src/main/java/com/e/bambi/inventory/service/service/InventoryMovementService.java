package com.e.bambi.inventory.service.service;

import com.e.bambi.inventory.service.model.InventoryMovement;
import com.e.bambi.inventory.service.dto.InventoryMovementFilterInputDTO;
import com.e.bambi.inventory.service.dto.InventoryMovementInputDTO;
import com.e.bambi.inventory.service.dto.PaginatedResultOutputDTO;
import com.e.bambi.inventory.service.exception.InventoryMovementBadRequestException;
import com.e.bambi.inventory.service.exception.InventoryMovementNotFoundException;
import com.e.bambi.inventory.service.mapper.InventoryMovementMapper;
import com.e.bambi.inventory.service.repository.InventoryMovementCrudRepository;
import com.e.bambi.inventory.service.repository.InventoryMovementRepositoryCustom;
import com.e.bambi.inventory.service.repository.MovementTypeCrudRepository;
import com.e.bambi.inventory.service.repository.ProductCrudRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryMovementService {

    private final InventoryMovementMapper inventoryMovementMapper;
    private final ProductCrudRepository productCrudRepository;
    private final MovementTypeCrudRepository movementTypeCrudRepository;
    private final InventoryMovementCrudRepository inventoryMovementCrudRepository;
    private final InventoryMovementRepositoryCustom inventoryMovementRepositoryCustom;

    public Mono<PaginatedResultOutputDTO> findInventoriesWithPaginationAndFilters(InventoryMovementFilterInputDTO filters) {
        return inventoryMovementRepositoryCustom.findInventoriesWithPaginationAndFilters(filters)
                .switchIfEmpty(Mono.error(new InventoryMovementNotFoundException("Inventory movement/s not found for the given filters")));
    }

    public Mono<ResponseEntity<InventoryMovement>> findInventoryMovementById(UUID inventoryMovementId) {
        return inventoryMovementCrudRepository.findById(inventoryMovementId)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.error(new InventoryMovementNotFoundException("Inventory movement not found.")));
    }

    public Mono<ResponseEntity<Object>> saveInventoryMovement(InventoryMovementInputDTO inventoryMovementInputDTO) {
        InventoryMovement inventoryMovement = inventoryMovementMapper.inventoryMovementInputDTOToInventoryMovement(inventoryMovementInputDTO);

        return Mono.zip(
                productCrudRepository.existsById(inventoryMovement.getProductId()),
                movementTypeCrudRepository.existsById(inventoryMovement.getMovementTypeId())
        ).flatMap(results -> {
            List<String> errors = getErrors(results);

            if (errors.isEmpty()) {
                return inventoryMovementCrudRepository.save(inventoryMovement)
                        .map(savedInventoryMovement -> {
                            String location = "/v1/inventory-movements/" + savedInventoryMovement.getId();

                            return ResponseEntity.created(URI.create(location)).build();
                        });
            }
            return Mono.error(new InventoryMovementBadRequestException("Invalid data.", errors.toArray()));
        });
    }

    private static List<String> getErrors(Tuple2<Boolean, Boolean> results) {
        boolean productExists = results.getT1();
        boolean movementTypeExists = results.getT2();

        List<String> errors = new ArrayList<>();

        if (!productExists) {
            errors.add("Product not found for the given id.");
        }
        if (!movementTypeExists) {
            errors.add("The selected movement type is not valid.");
        }
        return errors;
    }

    public Mono<ResponseEntity<Object>> updateInventoryMovementById(UUID inventoryMovementId, InventoryMovementInputDTO inventoryMovementInputDTO) {
        InventoryMovement inventoryMovement = inventoryMovementMapper.inventoryMovementInputDTOToInventoryMovement(inventoryMovementInputDTO);

        return inventoryMovementCrudRepository.findById(inventoryMovementId)
                .flatMap(inventoryMovementObtained -> {
                    inventoryMovement.setId(inventoryMovementId);
                    if (!inventoryMovementObtained.equals(inventoryMovement)) {
                        return inventoryMovementCrudRepository.save(inventoryMovement)
                                .thenReturn(ResponseEntity.noContent().build())
                                .onErrorResume(DuplicateKeyException.class, e -> {
                                    return Mono.error(new DuplicateKeyException("Duplicate entry detected. Please ensure the data is unique."));
                                });
                    }
                    return Mono.just(ResponseEntity.noContent().build());
                })
                .switchIfEmpty(Mono.error(new InventoryMovementNotFoundException("Inventory movement not found.")));
    }

    public Mono<ResponseEntity<Object>> deleteInventoryMovementById(UUID inventoryMovementId) {
        return inventoryMovementCrudRepository.existsById(inventoryMovementId)
                .flatMap(inventoryMovementExists -> {
                    if (inventoryMovementExists) {
                        return inventoryMovementCrudRepository.deleteById(inventoryMovementId)
                                .thenReturn(ResponseEntity.noContent().build());
                    }
                    return Mono.error(new InventoryMovementNotFoundException("Inventory movement not found."));
                });
    }
}
