package com.e.bambi.order.service.service;

import com.e.bambi.order.service.dto.command.create.order.CreateOrderCommand;
import com.e.bambi.order.service.dto.command.create.order.CreateOrderItemCommand;
import com.e.bambi.order.service.dto.queries.OrderQuery;
import com.e.bambi.order.service.dto.queries.PaginatedResultResponse;
import com.e.bambi.order.service.dto.queries.order.OrderResponse;
import com.e.bambi.order.service.exception.OrderNotFoundException;
import com.e.bambi.order.service.exception.PaymentMethodNotFoundException;
import com.e.bambi.order.service.mapper.OrderItemMapper;
import com.e.bambi.order.service.mapper.OrderMapper;
import com.e.bambi.order.service.model.Order;
import com.e.bambi.order.service.model.OrderItem;
import com.e.bambi.order.service.model.OrderStatusEnum;
import com.e.bambi.order.service.repository.OrderCrudRepository;
import com.e.bambi.order.service.repository.OrderItemCrudRepository;
import com.e.bambi.order.service.repository.OrderRepositoryCustom;
import com.e.bambi.order.service.repository.PaymentMethodCrudRepository;
import com.e.bambi.order.service.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final SecurityUtils securityUtils;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final OrderCrudRepository orderCrudRepository;
    private final OrderRepositoryCustom orderRepositoryCustom;
    private final OrderItemCrudRepository orderItemCrudRepository;
    private final PaymentMethodCrudRepository paymentMethodCrudRepository;

    public Flux<OrderResponse> findOrdersByUserId(UUID userId) {
        return orderRepositoryCustom.findOrdersByUserId(userId)
                .switchIfEmpty(Flux.error(new OrderNotFoundException("Orders not found for the given user.")));
    }

    public Mono<ResponseEntity<OrderResponse>> findOrderByUserIdAndOrderId(UUID userId, UUID orderId) {
        return orderRepositoryCustom.findOrderByUserIdAndOrderId(userId, orderId)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.error(new OrderNotFoundException("Order not found.")));
    }

    public Mono<PaginatedResultResponse> findOrdersWithPaginationAndFilters(OrderQuery orderQuery) {
        return orderRepositoryCustom.findAllOrders(orderQuery)
                .switchIfEmpty(Mono.error(new OrderNotFoundException("Order/s not found for the given filters.")));
    }

    public Mono<ResponseEntity<OrderResponse>> findOrderByOrderId(UUID orderId) {
        return orderRepositoryCustom.findOrderByOrderId(orderId)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.error(new OrderNotFoundException("Order not found for the given id.")));
    }

    @Transactional
    public Mono<ResponseEntity<Object>> saveOrder(CreateOrderCommand createOrderCommand) {
        return securityUtils.getCurrentUserId()
                .flatMap(userId ->
                        paymentMethodCrudRepository.existsById(createOrderCommand.getPaymentMethodId())
                                .filter(Boolean::booleanValue)
                                .switchIfEmpty(Mono.error(new PaymentMethodNotFoundException("Método de pago no encontrado")))
                                .flatMap(__ -> {
                                    Order order = orderMapper.createOrderCommandToOrder(createOrderCommand);
                                    order.setUserId(userId);
                                    order.setStatusId(OrderStatusEnum.PENDING.getValue());

                                    return orderCrudRepository.save(order);
                                })
                                .flatMap(savedOrder ->
                                        orderItemCrudRepository.saveAll(createItems(savedOrder.getId(), createOrderCommand.getItems()))
                                                .then(Mono.just(savedOrder))
                                                .map(order ->
                                                        ResponseEntity.created(URI.create("/v1/orders/" + order.getId())).build()))
                );
    }

    private Flux<OrderItem> createItems(UUID orderId, List<CreateOrderItemCommand> items) {
        return Flux.fromIterable(items)
                .map(item -> orderItemMapper.createOrderItemCommandToOrderItem(item, orderId));
    }
}
