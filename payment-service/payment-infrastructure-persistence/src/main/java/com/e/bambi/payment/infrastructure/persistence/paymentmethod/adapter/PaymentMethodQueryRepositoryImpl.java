package com.e.bambi.payment.infrastructure.persistence.paymentmethod.adapter;

import com.e.bambi.payment.application.paymentmethod.dto.response.PaymentMethodReadResponse;
import com.e.bambi.payment.application.paymentmethod.dto.response.PaymentMethodResponse;
import com.e.bambi.payment.application.paymentmethod.port.outbound.repository.PaymentMethodQueryRepository;
import com.e.bambi.payment.infrastructure.persistence.paymentmethod.repository.PaymentMethodR2dbcRepository;
import com.e.bambi.shared.kernel.domain.valueobject.PaymentMethodId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PaymentMethodQueryRepositoryImpl implements PaymentMethodQueryRepository {

    private final PaymentMethodR2dbcRepository paymentMethodR2dbcRepository;

    @Override
    public Mono<PaymentMethodReadResponse> findById(PaymentMethodId paymentMethodId) {
        return paymentMethodR2dbcRepository
                .paymentMethodFindById(paymentMethodId.getValue());
    }

    @Override
    public Flux<PaymentMethodResponse> findAll() {
        return paymentMethodR2dbcRepository.paymentMethodFindAll();
    }
}
