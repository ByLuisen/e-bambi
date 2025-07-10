package com.e.bambi.inventory.application.inventorymovement.handler.command;

import com.e.bambi.inventory.application.inventorymovement.dto.command.CreateInventoryMovementCommand;
import com.e.bambi.inventory.application.inventorymovement.dto.response.InventoryMovementResponse;
import com.e.bambi.inventory.application.inventorymovement.mapper.InventoryMovementApplicationMapper;
import com.e.bambi.inventory.application.inventorymovement.port.outbond.repository.InventoryMovementRepository;
import com.e.bambi.inventory.application.movementtype.port.outbond.repository.MovementTypeQueryRepository;
import com.e.bambi.inventory.application.offer.port.outbound.repository.OfferQueryRepository;
import com.e.bambi.inventory.application.product.port.outbond.repository.ProductQueryRepository;
import com.e.bambi.inventory.domain.inventorymovement.entity.InventoryMovement;
import com.e.bambi.inventory.domain.exception.InventoryMovementBadRequestException;
import com.e.bambi.inventory.domain.shared.valueobject.Stock;
import com.e.bambi.shared.kernel.application.bus.CommandHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateInventoryMovementCommandHandler implements
        CommandHandler<Mono<InventoryMovementResponse>, CreateInventoryMovementCommand> {

    private final InventoryMovementRepository inventoryMovementRepository;
    private final InventoryMovementApplicationMapper inventoryMovementApplicationMapper;
    private final OfferQueryRepository offerQueryRepository;
    private final ProductQueryRepository productQueryRepository;
    private final MovementTypeQueryRepository movementTypeQueryRepository;

    @Override
    @Transactional
    public Mono<InventoryMovementResponse> handle(CreateInventoryMovementCommand command) {
        InventoryMovement inventoryMovement =
                inventoryMovementApplicationMapper.createInventoryMovementCommandToInventoryMovement(command);

        return ensureInventoryMovementIsValid(inventoryMovement)
                .flatMap(productStock -> {
                    inventoryMovement.initializeInventoryMovement();
                    inventoryMovement.calculateStock(productStock, command.getQuantity());
                    return inventoryMovementRepository.insert(inventoryMovement)
                            .map(inventoryMovementApplicationMapper::toInventoryMovementResponse);
                });
    }

    private Mono<Stock> ensureInventoryMovementIsValid(InventoryMovement inventoryMovement) {
        List<String> errors = new ArrayList<>(3);

        Mono<Stock> supplierExists = offerQueryRepository.findOfferStock(inventoryMovement.getSupplierId(),
                inventoryMovement.getProduct().getId());
        Mono<Boolean> productExists = productQueryRepository.existsById(inventoryMovement.getProduct().getId());
        Mono<Boolean> movementTypeExists = movementTypeQueryRepository.existsById(inventoryMovement.getMovementTypeId());

        return Mono.zip(supplierExists, productExists, movementTypeExists)
                .switchIfEmpty(
                        Mono.error(new InventoryMovementBadRequestException("Inventory movement could not be created",
                                List.of("Product for supplier id: " + inventoryMovement.getSupplierId().getValue() +
                                        " and product id: " + inventoryMovement.getProduct().getId().getValue() +
                                        " does not exists"))))
                .flatMap(t -> {
                    if (!t.getT2()) {
                        errors.add("Product with id: " + inventoryMovement.getProduct().getId().getValue() +
                                " does not exists");
                    }
                    if (!t.getT3()) {
                        errors.add("Movement type with id: " + inventoryMovement.getMovementTypeId().getValue() +
                                " does not exists");
                    }

                    if (!errors.isEmpty()) {
                        log.error("Inventory movement could not be created");
                        return Mono.error(
                                new InventoryMovementBadRequestException("Inventory movement could not be created",
                                        errors));
                    }

                    return Mono.just(t.getT1());
                });
    }
}
