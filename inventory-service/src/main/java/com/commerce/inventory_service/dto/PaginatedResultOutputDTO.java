package com.commerce.inventory_service.dto;

import java.util.List;

public record PaginatedResultOutputDTO(List<?> data, Long count) {
}
