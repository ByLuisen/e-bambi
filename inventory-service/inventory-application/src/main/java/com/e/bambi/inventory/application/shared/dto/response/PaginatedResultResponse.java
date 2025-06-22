package com.e.bambi.inventory.application.shared.dto.response;

import java.util.List;

public record PaginatedResultResponse<T>(List<T> data, Long count) {
}
