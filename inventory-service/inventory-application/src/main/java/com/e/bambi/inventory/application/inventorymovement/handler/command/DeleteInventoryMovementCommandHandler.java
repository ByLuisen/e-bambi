package com.e.bambi.inventory.application.inventorymovement.handler.command;

import com.e.bambi.inventory.application.inventorymovement.dto.command.DeleteInventoryMovementCommand;
import com.e.bambi.inventory.application.inventorymovement.port.outbond.repository.InventoryMovementRepository;
import com.e.bambi.inventory.domain.exception.InventoryMovementNotFoundException;
import com.e.bambi.shared.kernel.application.bus.CommandHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeleteInventoryMovementCommandHandler implements CommandHandler<Mono<Void>, DeleteInventoryMovementCommand> {

    private final InventoryMovementRepository inventoryMovementRepository;

    @Override
    public Mono<Void> handle(DeleteInventoryMovementCommand command) {
        return inventoryMovementRepository
                .deleteById(command.getInventoryMovementId())
                .handle((updatedRows, sink) -> {
                    if (updatedRows < 1) {
                        log.error("Inventory movement with id: {} could not be deleted",
                                command.getInventoryMovementId().getValue());
                        sink.error(new InventoryMovementNotFoundException("Inventory movement with id: " +
                                command.getInventoryMovementId().getValue() + " could not be deleted"));
                    } else {
                        sink.complete();
                    }
                });
    }
}
