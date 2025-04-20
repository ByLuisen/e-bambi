package com.e.bambi.order.service.repository;

import com.e.bambi.order.service.model.OrderStatus;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface OrderStatusCrudRepository extends ReactiveCrudRepository<OrderStatus, UUID> {
}
