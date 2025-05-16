package com.e.bambi.order.application.dto.response;

import java.util.List;

public record PaginatedResultResponse<T>(List<T> data, Integer count) {
}
