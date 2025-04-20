package com.e.bambi.inventory.service.dto;

import java.util.List;

public record PaginatedResultOutputDTO(List<?> data, Long count) {
}
