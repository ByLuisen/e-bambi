package com.e.bambi.payment.application;

import com.e.bambi.payment.application.dto.command.ValidatePaymentCommand;
import com.e.bambi.payment.application.outbox.PaymentOutboxEventHelper;
import com.e.bambi.payment.domain.PaymentDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PaymentApplicationService {

    private final PaymentDomainService paymentDomainService;
    private final PaymentOutboxEventHelper paymentOutboxEventHelper;

    @Transactional
    public Mono<Void> validatePaymentMethod(ValidatePaymentCommand command) {
        return null;
    }
}
