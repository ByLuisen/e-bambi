package com.e.bambi.order.infrastructure.rest.order.controller;

import com.e.bambi.order.application.order.dto.query.OrderByIdAndUserIdQuery;
import com.e.bambi.order.application.order.dto.query.OrderByIdQuery;
import com.e.bambi.order.application.order.dto.query.OrderByUserIdQuery;
import com.e.bambi.order.application.order.dto.query.TrackOrderQuery;
import com.e.bambi.order.application.order.dto.response.CreateOrderResponse;
import com.e.bambi.order.application.order.dto.response.PaginatedResultResponse;
import com.e.bambi.order.application.order.dto.response.TrackOrderReadResponse;
import com.e.bambi.order.application.order.dto.response.ordersummary.OrderSummaryReadResponse;
import com.e.bambi.order.application.order.dto.response.orderwithdetails.OrderWithDetailReadResponse;
import com.e.bambi.order.infrastructure.rest.order.dto.request.OrderByUserIdRequestDto;
import com.e.bambi.order.infrastructure.rest.order.dto.request.OrderRequestDto;
import com.e.bambi.order.infrastructure.rest.order.dto.request.createorder.CreateOrderRequestDto;
import com.e.bambi.order.infrastructure.rest.order.mapper.OrderRestMapper;
import com.e.bambi.shared.kernel.application.port.inbound.bus.CommandBus;
import com.e.bambi.shared.kernel.application.port.inbound.bus.QueryBus;
import com.e.bambi.shared.kernel.domain.valueobject.OrderId;
import com.e.bambi.shared.kernel.domain.valueobject.UserId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class OrderController {

    private final OrderRestMapper orderRestMapper;
    private final QueryBus queryBus;
    private final CommandBus commandBus;

    @GetMapping("/me/orders")
    public Mono<PaginatedResultResponse<OrderSummaryReadResponse>>
    getUserOrders(JwtAuthenticationToken auth, @Valid OrderByUserIdRequestDto orderByUserIdDto) {
        return queryBus.dispatch(
                new OrderByUserIdQuery(
                        new UserId(java.util.UUID.fromString(auth.getToken().getClaimAsString("sub"))),
                        orderByUserIdDto.getPage(),
                        Integer.parseInt(orderByUserIdDto.getDate())
                )
        );
    }

    @PostMapping("/me/orders")
    public Mono<ResponseEntity<CreateOrderResponse>> createOrder(JwtAuthenticationToken auth,
                                                                 @RequestBody @Valid
                                                                 CreateOrderRequestDto createOrderRequestDTO) {
        return commandBus.dispatch(orderRestMapper
                .toCreateOrderCommand(
                        auth.getToken().getClaimAsString("sub"),
                        createOrderRequestDTO
                )
        ).map(ResponseEntity::ok);
    }

    @GetMapping("/me/orders/{orderId}")
    public Mono<ResponseEntity<OrderWithDetailReadResponse>> getUserOrder(JwtAuthenticationToken auth,
                                                                          @PathVariable @UUID String orderId) {
        return queryBus
                .dispatch(new OrderByIdAndUserIdQuery(
                        new UserId(java.util.UUID.fromString(auth.getToken().getClaimAsString("sub"))),
                        new OrderId(java.util.UUID.fromString(orderId))
                ))
                .map(ResponseEntity::ok);
    }

    @GetMapping("/me/orders/{orderId}/tracking")
    public Mono<ResponseEntity<TrackOrderReadResponse>> trackOrder(JwtAuthenticationToken auth,
                                                                   @PathVariable @UUID String orderId) {
        return queryBus
                .dispatch(new TrackOrderQuery(
                        new UserId(java.util.UUID.fromString(auth.getToken().getClaimAsString("sub"))),
                        new OrderId(java.util.UUID.fromString(orderId))
                )).map(ResponseEntity::ok);
    }

    @GetMapping("/orders")
    public Mono<ResponseEntity<PaginatedResultResponse<OrderSummaryReadResponse>>> searchOrders(@Valid
                                                                                                OrderRequestDto
                                                                                                        orderRequestDTO) {
        return queryBus
                .dispatch(
                        orderRestMapper.toOrderQuery(orderRequestDTO)
                )
                .map(ResponseEntity::ok);
    }

    @GetMapping("/orders/{orderId}")
    public Mono<ResponseEntity<OrderWithDetailReadResponse>> getOrderById(@PathVariable @UUID String orderId) {
        return queryBus
                .dispatch(new OrderByIdQuery(
                        new OrderId(java.util.UUID.fromString(orderId))
                ))
                .map(ResponseEntity::ok);
    }
}
