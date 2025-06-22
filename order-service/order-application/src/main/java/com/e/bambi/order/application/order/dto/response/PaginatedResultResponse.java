package com.e.bambi.order.application.order.dto.response;

import java.util.List;

public record PaginatedResultResponse<T>(List<T> data, Long count) {
}
