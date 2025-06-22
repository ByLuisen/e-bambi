package com.e.bambi.inventory.application.movementtype.handler.command;

import com.e.bambi.inventory.application.movementtype.dto.command.CreateMovementTypeCommand;
import com.e.bambi.inventory.application.movementtype.dto.response.MovementTypeResponse;
import com.e.bambi.inventory.application.movementtype.mapper.MovementTypeApplicationMapper;
import com.e.bambi.inventory.application.movementtype.port.outbond.repository.MovementTypeRepository;
import com.e.bambi.inventory.domain.movementtype.entity.MovementType;
import com.e.bambi.shared.kernel.application.bus.CommandHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateMovementTypeCommandHandler implements
        CommandHandler<Mono<MovementTypeResponse>, CreateMovementTypeCommand> {

    private final MovementTypeRepository movementTypeRepository;
    private final MovementTypeApplicationMapper movementTypeApplicationMapper;

    @Override
    public Mono<MovementTypeResponse> handle(CreateMovementTypeCommand command) {
        MovementType movementType =
                movementTypeApplicationMapper.createMovementTypeCommandToMovementType(command);
        movementType.initializeMovementType();

        return movementTypeRepository.insert(movementType)
                .onErrorMap(DuplicateKeyException.class, e -> {
                    log.error("Movement type with name: {} already exists", command.getName());
                    return new DuplicateKeyException("Movement type with name: " + command.getName() +
                            " already exists");
                })
                .map(movementTypeApplicationMapper::toMovementTypeResponse);
    }
}
