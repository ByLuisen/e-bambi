package com.e.bambi.payment.infrastructure.persistence.paymentmethod.repository;

import com.e.bambi.payment.application.paymentmethod.dto.response.PaymentMethodReadResponse;
import com.e.bambi.payment.application.paymentmethod.dto.response.PaymentMethodResponse;
import com.e.bambi.payment.infrastructure.persistence.paymentmethod.entity.PaymentMethodEntity;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface PaymentMethodR2dbcRepository extends R2dbcRepository<PaymentMethodEntity, UUID> {

    @Modifying
    @Query("DELETE FROM payment_methods WHERE id = :paymentMethodId")
    Mono<Integer> deletePaymentMethodById(UUID paymentMethodId);

    @Query("SELECT name, description FROM payment_methods WHERE id = :paymentMethodId")
    Mono<PaymentMethodReadResponse> paymentMethodFindById(UUID paymentMethodId);

    @Query("SELECT id, name, description FROM payment_methods")
    Flux<PaymentMethodResponse> paymentMethodFindAll();
}
