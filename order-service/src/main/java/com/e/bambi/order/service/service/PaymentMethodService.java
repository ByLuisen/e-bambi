package com.e.bambi.order.service.service;

import com.e.bambi.order.service.dto.command.create.CreatePaymentMethodCommand;
import com.e.bambi.order.service.dto.command.update.UpdatePaymentMethodCommand;
import com.e.bambi.order.service.exception.PaymentMethodNotFoundException;
import com.e.bambi.order.service.mapper.PaymentMethodMapper;
import com.e.bambi.order.service.model.PaymentMethod;
import com.e.bambi.order.service.repository.PaymentMethodCrudRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentMethodService {

    private final PaymentMethodMapper paymentMethodMapper;
    private final PaymentMethodCrudRepository paymentMethodCrudRepository;

    public Flux<PaymentMethod> findAllPaymentMethods() {
        return paymentMethodCrudRepository.findAll()
                .switchIfEmpty(Flux.error(new PaymentMethodNotFoundException("Payment methods no recorded.")));
    }

    public Mono<ResponseEntity<Object>> savePaymentMethod(CreatePaymentMethodCommand createPaymentMethodCommand) {
        PaymentMethod paymentMethod = paymentMethodMapper
                .createPaymentMethodCommandToPaymentMethod(createPaymentMethodCommand);

        return paymentMethodCrudRepository.save(paymentMethod)
                .map(savedPaymentMethod -> {
                    String location = "v1/payment-methods/" + paymentMethod.getId();

                    return ResponseEntity.created(URI.create(location)).build();
                })
                .onErrorResume(DuplicateKeyException.class, e -> {
                    return Mono.error(new DuplicateKeyException("Duplicate entry detected. Please ensure " +
                            "the data is unique."));
                });
    }

    public Mono<ResponseEntity<Object>> updatePaymentMethodById(UUID paymentMethodId,
                                                                UpdatePaymentMethodCommand updatePaymentMethodCommand) {
        PaymentMethod paymentMethod = paymentMethodMapper
                .updatePaymentMethodCommandToPaymentMethod(updatePaymentMethodCommand);

        return paymentMethodCrudRepository.findById(paymentMethodId)
                .flatMap(obtainedPaymentMethod -> {
                    paymentMethod.setId(paymentMethodId);
                    if (!obtainedPaymentMethod.equals(paymentMethod)) {
                        return paymentMethodCrudRepository.save(paymentMethod)
                                .thenReturn(ResponseEntity.noContent().build())
                                .onErrorResume(DuplicateKeyException.class, e -> {
                                    return Mono.error(new DuplicateKeyException("Duplicate entry detected. Please " +
                                            "ensure the data is unique."));
                                });
                    }
                    return Mono.just(ResponseEntity.noContent().build());
                }).switchIfEmpty(Mono.error(new PaymentMethodNotFoundException("Payment method not found.")));
    }

    public Mono<ResponseEntity<Object>> deletePaymentMethodById(UUID paymentMethodId) {
        return paymentMethodCrudRepository.existsById(paymentMethodId)
                .flatMap(existsPaymentMethod -> {
                    if (existsPaymentMethod) {
                        return paymentMethodCrudRepository.deleteById(paymentMethodId)
                                .thenReturn(ResponseEntity.noContent().build());
                    }
                    return Mono.error(new PaymentMethodNotFoundException("Payment method not found."));
                });
    }
}
