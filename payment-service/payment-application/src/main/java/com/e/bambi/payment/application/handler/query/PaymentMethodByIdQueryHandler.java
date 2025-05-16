package com.e.bambi.payment.application.handler.query;

import com.e.bambi.payment.application.dto.query.PaymentMethodByIdQuery;
import com.e.bambi.payment.application.mapper.PaymentMethodApplicationMapper;
import com.e.bambi.payment.application.port.outbound.repository.PaymentMethodRepository;
import com.e.bambi.payment.application.dto.response.PaymentMethodResponse;
import com.e.bambi.payment.domain.exception.PaymentMethodNotFoundException;
import com.e.bambi.shared.kernel.application.bus.QueryHandler;
import com.e.bambi.shared.kernel.domain.valueobject.PaymentMethodId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PaymentMethodByIdQueryHandler implements QueryHandler<Mono<PaymentMethodResponse>, PaymentMethodByIdQuery> {

    private final PaymentMethodRepository paymentMethodRepository;
    private final PaymentMethodApplicationMapper paymentMethodApplicationMapper;

    @Override
    public Mono<PaymentMethodResponse> handle(PaymentMethodByIdQuery query) {
        PaymentMethodId paymentMethodId = query.paymentMethodId();

        return paymentMethodRepository.findById(paymentMethodId)
                .switchIfEmpty(
                        Mono.error(new PaymentMethodNotFoundException("Payment method with " +
                                "id: " + paymentMethodId.getValue() + " could not be found"))
                )
                .map(paymentMethodApplicationMapper::toPaymentMethodResponse);
    }
}
