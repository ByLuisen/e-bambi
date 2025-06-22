package com.e.bambi.inventory.application.movementtype.dto.response;

import java.util.UUID;

public record MovementTypeResponse(
        UUID id,
        String name,
        String description
) {
}
