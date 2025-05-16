package com.e.bambi.payment.application.handler.query;

import com.e.bambi.payment.application.dto.query.PaymentMethodFindAllQuery;
import com.e.bambi.payment.application.dto.response.PaymentMethodResponse;
import com.e.bambi.payment.application.mapper.PaymentMethodApplicationMapper;
import com.e.bambi.payment.application.port.outbound.repository.PaymentMethodRepository;
import com.e.bambi.payment.domain.exception.PaymentMethodNotFoundException;
import com.e.bambi.shared.kernel.application.bus.QueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
public class PaymentMethodFindAllQueryHandler implements QueryHandler<Flux<PaymentMethodResponse>, PaymentMethodFindAllQuery> {

    private final PaymentMethodRepository paymentMethodRepository;
    private final PaymentMethodApplicationMapper paymentMethodApplicationMapper;

    @Override
    public Flux<PaymentMethodResponse> handle(PaymentMethodFindAllQuery query) {
        return paymentMethodRepository.findAll()
                .switchIfEmpty(
                        Flux.error(new PaymentMethodNotFoundException("Payment methods could not be found"))
                )
                .map(paymentMethodApplicationMapper::toPaymentMethodResponse);
    }
}
