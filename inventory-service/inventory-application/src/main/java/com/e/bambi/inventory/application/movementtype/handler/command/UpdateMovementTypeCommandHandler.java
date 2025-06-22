package com.e.bambi.inventory.application.movementtype.handler.command;


import com.e.bambi.inventory.application.movementtype.dto.command.UpdateMovementTypeCommand;
import com.e.bambi.inventory.application.movementtype.dto.response.MovementTypeResponse;
import com.e.bambi.inventory.application.movementtype.mapper.MovementTypeApplicationMapper;
import com.e.bambi.inventory.application.movementtype.port.outbond.repository.MovementTypeRepository;
import com.e.bambi.inventory.domain.exception.MovementTypeNotFoundException;
import com.e.bambi.shared.kernel.application.bus.CommandHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateMovementTypeCommandHandler implements
        CommandHandler<Mono<MovementTypeResponse>, UpdateMovementTypeCommand> {

    private final MovementTypeRepository movementTypeRepository;
    private final MovementTypeApplicationMapper movementTypeApplicationMapper;

    @Override
    public Mono<MovementTypeResponse> handle(UpdateMovementTypeCommand command) {
        return movementTypeRepository.update(movementTypeApplicationMapper
                        .updateMovementTypeCommandToMovementType(command)
                ).switchIfEmpty(
                        Mono.error(new MovementTypeNotFoundException("Movement type with id: " +
                                command.getMovementTypeId().getValue() + " could not be found"))
                ).onErrorMap(DuplicateKeyException.class, e -> {
                    log.error("Movement type with name: {} already exists", command.getName());
                    return new DuplicateKeyException("Movement type with name: " + command.getName() +
                            " already exists");
                })
                .map(movementTypeApplicationMapper::toMovementTypeResponse);
    }
}
