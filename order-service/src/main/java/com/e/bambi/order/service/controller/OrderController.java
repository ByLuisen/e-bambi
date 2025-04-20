package com.e.bambi.order.service.controller;

import com.e.bambi.order.service.dto.command.create.order.CreateOrderCommand;
import com.e.bambi.order.service.dto.queries.OrderQuery;
import com.e.bambi.order.service.dto.queries.PaginatedResultResponse;
import com.e.bambi.order.service.dto.queries.order.OrderResponse;
import com.e.bambi.order.service.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/users/{userId}/orders")
    @PreAuthorize("#userId == authentication.principal.claims['sub']")
    public Flux<OrderResponse> findOrdersByUserId(@PathVariable @UUID String userId) {
        return orderService.findOrdersByUserId(java.util.UUID.fromString(userId));
    }

    @GetMapping("/users/{userId}/orders/{orderId}")
    @PreAuthorize("#userId == authentication.principal.claims['sub']")
    public Mono<ResponseEntity<OrderResponse>> findOrderByUserIdAndOrderId(@PathVariable @UUID String userId,
                                                                           @PathVariable @UUID String orderId) {
        return orderService.findOrderByUserIdAndOrderId(java.util.UUID.fromString(userId), java.util.UUID.fromString(orderId));
    }

    @GetMapping("/orders")
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<PaginatedResultResponse> findOrdersWithPaginationAndFilters(@Valid OrderQuery orderQuery) {
        return orderService.findOrdersWithPaginationAndFilters(orderQuery);
    }

    @GetMapping("/orders/{orderId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ResponseEntity<OrderResponse>> findOrderByOrderId(@PathVariable @UUID String orderId) {
        return orderService.findOrderByOrderId(java.util.UUID.fromString(orderId));
    }

    @PostMapping("/orders")
    public Mono<ResponseEntity<Object>> saveOrder(@RequestBody @Valid CreateOrderCommand orderInputDTO) {
        return orderService.saveOrder(orderInputDTO);
    }
}
