package com.e.bambi.payment.application.paymentmethod.handler.command.message;

import com.e.bambi.payment.application.outbox.PaymentOutboxEventHelper;
import com.e.bambi.payment.domain.event.PaymentAggregateType;
import com.e.bambi.payment.application.paymentmethod.dto.command.message.ValidatePaymentCommand;
import com.e.bambi.payment.application.paymentmethod.port.outbound.repository.PaymentMethodRepository;
import com.e.bambi.payment.domain.PaymentDomainService;
import com.e.bambi.payment.domain.event.PaymentMethodEvent;
import com.e.bambi.payment.domain.event.PaymentMethodValidationFailedEvent;
import com.e.bambi.shared.kernel.application.bus.CommandHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ValidatePaymentCommandHandler implements CommandHandler<Mono<Void>, ValidatePaymentCommand> {

    private final PaymentDomainService paymentDomainService;
    private final PaymentMethodRepository paymentMethodRepository;
    private final PaymentOutboxEventHelper paymentOutboxEventHelper;

    @Override
    @Transactional
    public Mono<Void> handle(ValidatePaymentCommand command) {
        return validatePayment(command);
    }

    private Mono<Void> validatePayment(ValidatePaymentCommand command) {
        return isPaymentOutboxEventProcessed(command.getSagaId())
                .flatMap(existsPaymentOutboxEvent -> {

                    if (existsPaymentOutboxEvent) {
                        log.info("A payment outbox event with saga id: {}, and order id: {} is already processed",
                                command.getSagaId(), command.getOrderId().getValue());
                        return Mono.empty();
                    }

                    log.info("Validating payment for order id: {}", command.getOrderId().getValue());
                    return validatePaymentMethodId(command)
                            .flatMap(paymentMethodEvent ->
                                    paymentOutboxEventHelper
                                            .savePaymentOutboxEvent(
                                                    paymentMethodEvent.getAggregatetype(),
                                                    paymentMethodEvent.toPayload(),
                                                    command.getSagaId()
                                            )
                            );
                });
    }

    private Mono<Boolean> isPaymentOutboxEventProcessed(String aggregateid) {
        return paymentOutboxEventHelper
                .existsPaymentOutboxEventByAggregateidAndAggregateType(aggregateid,
                        PaymentAggregateType.VALIDATED.getValue(),
                        PaymentAggregateType.VALIDATION_FAILED.getValue());
    }

    private Mono<PaymentMethodEvent> validatePaymentMethodId(ValidatePaymentCommand command) {
        return paymentMethodRepository.findById(command.getPaymentMethodId())
                .map(paymentMethod -> {
                            log.info("Payment method successfully validated for order id: {}",
                                    command.getOrderId().getValue());
                            return (PaymentMethodEvent) paymentDomainService.validatePayment(
                                    PaymentAggregateType.VALIDATED.getValue(),
                                    command.getOrderId());
                        }
                ).switchIfEmpty(Mono.defer(() -> {
                            log.error("Error while validating payment method for order id: {}",
                                    command.getOrderId().getValue());
                            return Mono.just(new PaymentMethodValidationFailedEvent(
                                    PaymentAggregateType.VALIDATION_FAILED.getValue(),
                                    command.getOrderId(),
                                    List.of("The provided payment method ID doesn't exist")
                            ));
                        }
                ));
    }
}
