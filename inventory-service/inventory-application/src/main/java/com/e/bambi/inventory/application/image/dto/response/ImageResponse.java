package com.e.bambi.inventory.application.image.dto.response;

import java.util.UUID;

public record ImageResponse(
        UUID id,
        String imageUrl
) {
}
