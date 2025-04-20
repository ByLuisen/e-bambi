package com.e.bambi.order.service.controller;

import com.e.bambi.order.service.dto.queries.OrderWithStatusHistoryResponse;
import com.e.bambi.order.service.service.OrderStatusHistoryService;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class OrderStatusHistoryController {

    private final OrderStatusHistoryService orderStatusHistoryService;

    @GetMapping("/orders/{orderId}/status-history")
    public Mono<ResponseEntity<OrderWithStatusHistoryResponse>> findOrderStatusHistoryByOrderId(@PathVariable @UUID
                                                                                                String orderId) {
        return orderStatusHistoryService.findOrderStatusHistoryByOrderId(java.util.UUID.fromString(orderId));
    }
}
