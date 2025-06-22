package com.e.bambi.inventory.application.productstatus.dto.response;

import java.util.UUID;

public record ProductStatusResponse(
        UUID id,
        String name
) {
}
