package com.e.bambi.inventory.application.movementtype.dto.command;

import com.e.bambi.inventory.application.movementtype.dto.response.MovementTypeResponse;
import com.e.bambi.inventory.domain.shared.valueobject.MovementTypeId;
import com.e.bambi.shared.kernel.application.bus.Command;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Getter
@RequiredArgsConstructor
public class UpdateMovementTypeCommand extends Command<Mono<MovementTypeResponse>> {
    private final MovementTypeId movementTypeId;
    private final String name;
    private final String description;
}