package com.e.bambi.inventory.application.movementtype.dto.command;

import com.e.bambi.inventory.application.movementtype.dto.response.MovementTypeResponse;
import com.e.bambi.shared.kernel.application.bus.Command;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Getter
@RequiredArgsConstructor
public class CreateMovementTypeCommand extends Command<Mono<MovementTypeResponse>> {
    private final String name;
    private final String description;
}
