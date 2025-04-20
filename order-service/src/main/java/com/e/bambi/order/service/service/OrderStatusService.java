package com.e.bambi.order.service.service;

import com.e.bambi.order.service.dto.command.create.CreateOrderStatusCommand;
import com.e.bambi.order.service.dto.command.update.UpdateOrderStatusCommand;
import com.e.bambi.order.service.exception.OrderStatusNotFoundException;
import com.e.bambi.order.service.mapper.OrderStatusMapper;
import com.e.bambi.order.service.model.OrderStatus;
import com.e.bambi.order.service.repository.OrderStatusCrudRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderStatusService {

    private final OrderStatusMapper orderStatusMapper;
    private final OrderStatusCrudRepository orderStatusCrudRepository;

    public Flux<OrderStatus> findAllOrderStatuses() {
        return orderStatusCrudRepository.findAll()
                .switchIfEmpty(Flux.error(new OrderStatusNotFoundException("No order statuses recorded.")));
    }

    public Mono<ResponseEntity<Object>> saveOrderStatus(CreateOrderStatusCommand createOrderStatusCommand) {
        OrderStatus orderStatus = orderStatusMapper.createOrderStatusCommandToOrderStatus(createOrderStatusCommand);

        return orderStatusCrudRepository.save(orderStatus)
                .map(savedOrderStatus -> {
                    String location = "/v1/order-statuses/" + savedOrderStatus.getId();

                    return ResponseEntity.created(URI.create(location)).build();
                })
                .onErrorResume(DuplicateKeyException.class, e -> {
                    return Mono.error(new DuplicateKeyException("Duplicate entry detected. Please ensure " +
                            "the data is unique."));
                });
    }

    public Mono<ResponseEntity<Object>> updateOrderStatusById(UUID orderStatusId,
                                                              UpdateOrderStatusCommand updateOrderStatusCommand) {
        OrderStatus orderStatus = orderStatusMapper.udpateOrderStatusCommandToOrderStatus(updateOrderStatusCommand);

        return orderStatusCrudRepository.findById(orderStatusId)
                .flatMap(orderStatusObtained -> {
                    orderStatus.setId(orderStatusId);
                    if (!orderStatusObtained.equals(orderStatus)) {
                        return orderStatusCrudRepository.save(orderStatus)
                                .thenReturn(ResponseEntity.noContent().build())
                                .onErrorResume(DuplicateKeyException.class, e -> {
                                    return Mono.error(new DuplicateKeyException("Duplicate entry detected. Please " +
                                            "ensure the data is unique."));
                                });
                    }
                    return Mono.just(ResponseEntity.noContent().build());
                }).switchIfEmpty(Mono.error(new OrderStatusNotFoundException("Order status not found.")));
    }

    public Mono<ResponseEntity<Object>> deleteOrderStatusById(UUID orderStatusId) {
        return orderStatusCrudRepository.existsById(orderStatusId)
                .flatMap(orderStatusExists -> {
                    if (orderStatusExists) {
                        return orderStatusCrudRepository.deleteById(orderStatusId)
                                .thenReturn(ResponseEntity.noContent().build());
                    }
                    return Mono.error(new OrderStatusNotFoundException("Order status not found."));
                });
    }
}
