package com.e.bambi.order.service.repository;

import com.e.bambi.order.service.model.Order;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface OrderCrudRepository extends ReactiveCrudRepository<Order, UUID> {
}
