package com.e.bambi.order.service.repository;

import com.e.bambi.order.service.model.PaymentMethod;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface PaymentMethodCrudRepository extends ReactiveCrudRepository<PaymentMethod, UUID> {
}
