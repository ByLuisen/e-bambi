package com.e.bambi.payment.infrastructure.persistence.repository;

import com.e.bambi.payment.infrastructure.persistence.entity.PaymentMethodEntity;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface PaymentMethodR2dbcRepository extends R2dbcRepository<PaymentMethodEntity, UUID> {

    @Modifying
    @Query("DELETE FROM payment_methods WHERE id = :paymentMethodId")
    Mono<Integer> deletePaymentMethodById(UUID paymentMethodId);
}
