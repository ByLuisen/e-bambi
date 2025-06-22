package com.e.bambi.inventory.application.brand.dto.response;

import java.util.UUID;

public record BrandResponse(
        UUID id,
        String name
) {
}
