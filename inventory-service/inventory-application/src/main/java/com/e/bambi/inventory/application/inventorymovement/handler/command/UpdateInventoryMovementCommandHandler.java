package com.e.bambi.inventory.application.inventorymovement.handler.command;

import com.e.bambi.inventory.application.inventorymovement.dto.command.UpdateInventoryMovementCommand;
import com.e.bambi.inventory.application.inventorymovement.dto.response.InventoryMovementResponse;
import com.e.bambi.inventory.application.inventorymovement.mapper.InventoryMovementApplicationMapper;
import com.e.bambi.inventory.application.inventorymovement.port.outbond.repository.InventoryMovementRepository;
import com.e.bambi.inventory.application.offer.port.outbound.repository.OfferQueryRepository;
import com.e.bambi.inventory.domain.exception.InventoryMovementNotFoundException;
import com.e.bambi.shared.kernel.application.bus.CommandHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateInventoryMovementCommandHandler implements
        CommandHandler<Mono<InventoryMovementResponse>, UpdateInventoryMovementCommand> {

    private final InventoryMovementRepository inventoryMovementRepository;
    private final InventoryMovementApplicationMapper inventoryMovementApplicationMapper;
    private final OfferQueryRepository offerQueryRepository;

    @Override
    @Transactional
    public Mono<InventoryMovementResponse> handle(UpdateInventoryMovementCommand command) {
        return inventoryMovementRepository.findById(command.getInventoryMovementId())
                .flatMap(inventoryMovement -> offerQueryRepository
                        .findOfferStock(
                                inventoryMovement.getSupplierId(),
                                inventoryMovement.getProduct().getId()
                        )
                        .flatMap(productStock -> {
                            inventoryMovement.calculateStock(productStock, command.getQuantity());
                            return inventoryMovementRepository
                                    .update(inventoryMovement)
                                    .map(inventoryMovementApplicationMapper::toInventoryMovementResponse);
                        })
                ).switchIfEmpty(
                        Mono.error(new InventoryMovementNotFoundException("Inventory movement with id: " +
                                command.getInventoryMovementId().getValue() + " could not be found"))
                );
    }
}
