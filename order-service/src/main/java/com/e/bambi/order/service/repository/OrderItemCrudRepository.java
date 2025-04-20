package com.e.bambi.order.service.repository;

import com.e.bambi.order.service.model.OrderItem;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface OrderItemCrudRepository extends ReactiveCrudRepository<OrderItem, UUID> {
}
