package com.commerce.inventory_service.dto;

import java.util.UUID;

public record MovementTypeOutputDTO(UUID id, String name, String description) {
}
