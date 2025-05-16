package com.e.bambi.order.rest.controller;

import com.e.bambi.order.application.dto.query.OrderByIdQuery;
import com.e.bambi.order.application.dto.query.OrderByUserIdAndOrderIdQuery;
import com.e.bambi.order.application.dto.query.OrderByUserIdQuery;
import com.e.bambi.order.application.dto.response.OrderResponse;
import com.e.bambi.order.application.dto.response.PaginatedResultResponse;
import com.e.bambi.shared.kernel.domain.valueobject.OrderId;
import com.e.bambi.order.rest.dto.request.CreateOrderRequestDTO;
import com.e.bambi.order.rest.dto.request.OrderQueryRequestDTO;
import com.e.bambi.order.rest.dto.request.TransitionOrderRequestDTO;
import com.e.bambi.order.rest.mapper.OrderRestMapper;
import com.e.bambi.shared.kernel.application.port.inbound.bus.CommandBus;
import com.e.bambi.shared.kernel.application.port.inbound.bus.QueryBus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class OrderController {

    private final OrderRestMapper orderRestMapper;
    private final QueryBus queryBus;
    private final CommandBus commandBus;

    @GetMapping("/me/orders")
    public Flux<OrderResponse> getUserOrders() {
        return queryBus.dispatch(new OrderByUserIdQuery());
    }

    @GetMapping("/me/orders/{orderId}")
    public Mono<ResponseEntity<OrderResponse>> getUserOrder(@PathVariable @UUID String orderId) {
        return queryBus
                .dispatch(new OrderByUserIdAndOrderIdQuery(
                        new OrderId(java.util.UUID.fromString(orderId))
                ))
                .map(ResponseEntity::ok);
    }

    @GetMapping("/orders")
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ResponseEntity<PaginatedResultResponse<OrderResponse>>> searchOrders(@Valid
                                                                                     OrderQueryRequestDTO
                                                                                             orderQueryRequestDTO) {
        return queryBus
                .dispatch(
                        orderRestMapper.toOrderQuery(orderQueryRequestDTO)
                )
                .map(ResponseEntity::ok);
    }

    @GetMapping("/orders/{orderId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ResponseEntity<OrderResponse>> getOrderById(@PathVariable @UUID String orderId) {
        return queryBus
                .dispatch(new OrderByIdQuery(
                        new OrderId(java.util.UUID.fromString(orderId))
                ))
                .map(ResponseEntity::ok);
    }

    @PostMapping("/orders")
    public Mono<ResponseEntity<Object>> createOrder(@RequestBody @Valid CreateOrderRequestDTO createOrderRequestDTO) {
        return commandBus
                .dispatch(orderRestMapper
                        .toCreateOrderCommand(createOrderRequestDTO)
                )
                .map(ResponseEntity::ok);
    }

    @PostMapping("/orders/{orderId}/transitions")
    public Mono<ResponseEntity<Object>> changeOrderStatus(@PathVariable @UUID String orderId,
                                                          @RequestBody @Valid
                                                          TransitionOrderRequestDTO transitionOrderRequestDTO) {
        return null;
    }
}
