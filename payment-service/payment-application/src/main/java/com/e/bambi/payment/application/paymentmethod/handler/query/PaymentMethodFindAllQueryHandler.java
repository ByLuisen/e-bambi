package com.e.bambi.payment.application.paymentmethod.handler.query;

import com.e.bambi.payment.application.paymentmethod.dto.query.PaymentMethodFindAllQuery;
import com.e.bambi.payment.application.paymentmethod.dto.response.PaymentMethodResponse;
import com.e.bambi.payment.application.paymentmethod.mapper.PaymentMethodApplicationMapper;
import com.e.bambi.payment.application.paymentmethod.port.outbound.repository.PaymentMethodQueryRepository;
import com.e.bambi.payment.domain.exception.PaymentMethodNotFoundException;
import com.e.bambi.shared.kernel.application.bus.QueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
public class PaymentMethodFindAllQueryHandler implements QueryHandler<Flux<PaymentMethodResponse>, PaymentMethodFindAllQuery> {

    private final PaymentMethodQueryRepository paymentMethodQueryRepository;
    private final PaymentMethodApplicationMapper paymentMethodApplicationMapper;

    @Override
    @Transactional(readOnly = true)
    public Flux<PaymentMethodResponse> handle(PaymentMethodFindAllQuery query) {
        return paymentMethodQueryRepository.findAll()
                .switchIfEmpty(
                        Flux.error(new PaymentMethodNotFoundException("Payment methods could not be found"))
                );
    }
}
