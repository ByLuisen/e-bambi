package com.e.bambi.inventory.service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ProductOutputDTO(UUID id, BrandOutputDTO brand, DepartmentOutputDTO department,
                               ProductStatusOutputDTO status, String sku, String name, BigDecimal price,
                               String description, LocalDateTime createdAt, LocalDateTime updatedAt,
                               List<ImageOutputDTO> images) {
}