package com.e.bambi.inventory.application.movementtype.handler.command;

import com.e.bambi.inventory.application.movementtype.dto.command.DeleteMovementTypeCommand;
import com.e.bambi.inventory.application.movementtype.port.outbond.repository.MovementTypeRepository;
import com.e.bambi.inventory.domain.exception.MovementTypeNotFoundException;
import com.e.bambi.shared.kernel.application.bus.CommandHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeleteMovementTypeCommandHandler implements CommandHandler<Mono<Void>, DeleteMovementTypeCommand> {

    private final MovementTypeRepository movementTypeRepository;

    @Override
    public Mono<Void> handle(DeleteMovementTypeCommand command) {
        UUID movementTypeId = command.getMovementTypeId().getValue();

        return movementTypeRepository.deleteById(command.getMovementTypeId())
                .handle((updatedRows, sink) -> {
                    if (updatedRows < 1) {
                        log.error("Movement type with id: {} could not be found", movementTypeId);
                        sink.error(new MovementTypeNotFoundException("Movement type with id: " + movementTypeId +
                                " could not be found"));
                    } else {
                        sink.complete();
                    }
                });
    }
}
