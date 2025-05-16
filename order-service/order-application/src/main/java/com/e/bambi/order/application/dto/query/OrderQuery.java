package com.e.bambi.order.application.dto.query;

import com.e.bambi.order.application.dto.response.OrderResponse;
import com.e.bambi.order.application.dto.response.PaginatedResultResponse;
import com.e.bambi.shared.kernel.application.bus.Query;
import lombok.Builder;
import lombok.Getter;
import reactor.core.publisher.Mono;

@Getter
@Builder
public class OrderQuery implements Query<Mono<PaginatedResultResponse<OrderResponse>>> {
    private String statusId;
    private String paymentMethodId;
    private String userId;
    private String createdAt;
    private String totalPrice;
    private String orderBy;
    private Integer page;
    private Integer size;
}
