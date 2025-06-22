package com.e.bambi.payment.application.paymentmethod.handler.query;

import com.e.bambi.payment.application.paymentmethod.dto.query.PaymentMethodByIdQuery;
import com.e.bambi.payment.application.paymentmethod.dto.response.PaymentMethodReadResponse;
import com.e.bambi.payment.application.paymentmethod.mapper.PaymentMethodApplicationMapper;
import com.e.bambi.payment.application.paymentmethod.port.outbound.repository.PaymentMethodQueryRepository;
import com.e.bambi.payment.domain.exception.PaymentMethodNotFoundException;
import com.e.bambi.shared.kernel.application.bus.QueryHandler;
import com.e.bambi.shared.kernel.domain.valueobject.PaymentMethodId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PaymentMethodByIdQueryHandler implements QueryHandler<Mono<PaymentMethodReadResponse>, PaymentMethodByIdQuery> {

    private final PaymentMethodQueryRepository paymentMethodQueryRepository;
    private final PaymentMethodApplicationMapper paymentMethodApplicationMapper;

    @Override
    @Transactional(readOnly = true)
    public Mono<PaymentMethodReadResponse> handle(PaymentMethodByIdQuery query) {
        PaymentMethodId paymentMethodId = query.getPaymentMethodId();

        return paymentMethodQueryRepository.findById(paymentMethodId)
                .switchIfEmpty(
                        Mono.error(new PaymentMethodNotFoundException("Payment method with " +
                                "id: " + paymentMethodId.getValue() + " could not be found"))
                );
    }
}
