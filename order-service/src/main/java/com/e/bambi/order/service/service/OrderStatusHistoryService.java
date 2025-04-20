package com.e.bambi.order.service.service;

import com.e.bambi.order.service.dto.queries.OrderWithStatusHistoryResponse;
import com.e.bambi.order.service.exception.OrderStatusHistoryNotFoundException;
import com.e.bambi.order.service.repository.OrderStatusHistoryCrudRepository;
import com.e.bambi.order.service.repository.OrderStatusHistoryRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderStatusHistoryService {

    private final OrderStatusHistoryRepositoryCustom orderStatusHistoryRepositoryCustom;
    private final OrderStatusHistoryCrudRepository orderStatusHistoryRepository;

    public Mono<ResponseEntity<OrderWithStatusHistoryResponse>> findOrderStatusHistoryByOrderId (UUID orderId) {
        return orderStatusHistoryRepositoryCustom.findStatusHistoryByOrderId(orderId)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.error(new OrderStatusHistoryNotFoundException("Order statuses not found " +
                        "for the given id.")));
    }
}
