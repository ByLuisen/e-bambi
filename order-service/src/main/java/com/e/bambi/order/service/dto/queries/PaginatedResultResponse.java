package com.e.bambi.order.service.dto.queries;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PaginatedResultResponse<T> {

    private List<T> data;
    private Long count;
}
