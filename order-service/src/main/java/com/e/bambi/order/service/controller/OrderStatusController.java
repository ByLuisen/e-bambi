package com.e.bambi.order.service.controller;

import com.e.bambi.order.service.dto.command.create.CreateOrderStatusCommand;
import com.e.bambi.order.service.dto.command.update.UpdateOrderStatusCommand;
import com.e.bambi.order.service.model.OrderStatus;
import com.e.bambi.order.service.service.OrderStatusService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/order-statuses")
public class OrderStatusController {

    private final OrderStatusService orderStatusService;

    @GetMapping
    public Flux<OrderStatus> findAllOrderStatuses() {
        return orderStatusService.findAllOrderStatuses();
    }

    @PostMapping
    public Mono<ResponseEntity<Object>> saveOrderStatus(@RequestBody @Valid
                                                        CreateOrderStatusCommand createOrderStatusCommand) {
        return orderStatusService.saveOrderStatus(createOrderStatusCommand);
    }

    @PutMapping("/{orderStatusId}")
    public Mono<ResponseEntity<Object>> updateOrderStatusById(@PathVariable @UUID String orderStatusId,
                                                              @RequestBody @Valid
                                                              UpdateOrderStatusCommand updateOrderStatusCommand) {
        return orderStatusService.updateOrderStatusById(
                java.util.UUID.fromString(orderStatusId), updateOrderStatusCommand);
    }

    @DeleteMapping("/{orderStatusId}")
    public Mono<ResponseEntity<Object>> deleteOrderStatusById(@PathVariable @UUID String orderStatusId) {
        return orderStatusService.deleteOrderStatusById(java.util.UUID.fromString(orderStatusId));
    }
}
