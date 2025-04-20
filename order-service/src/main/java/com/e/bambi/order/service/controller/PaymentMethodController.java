package com.e.bambi.order.service.controller;

import com.e.bambi.order.service.dto.command.create.CreatePaymentMethodCommand;
import com.e.bambi.order.service.dto.command.update.UpdatePaymentMethodCommand;
import com.e.bambi.order.service.model.PaymentMethod;
import com.e.bambi.order.service.service.PaymentMethodService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/payment-methods")
public class PaymentMethodController {

    private final PaymentMethodService paymentMethodService;

    @GetMapping
    public Flux<PaymentMethod> findAllPaymentMethods() {
        return paymentMethodService.findAllPaymentMethods();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ResponseEntity<Object>> savePaymentMethod(@RequestBody @Valid
                                                          CreatePaymentMethodCommand createPaymentMethodCommand) {
        return paymentMethodService.savePaymentMethod(createPaymentMethodCommand);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{paymentMethodId}")
    public Mono<ResponseEntity<Object>> updatePaymentMethodById(@PathVariable @UUID String paymentMethodId,
                                                                @RequestBody @Valid
                                                                UpdatePaymentMethodCommand updatePaymentMethodCommand) {
        return paymentMethodService.updatePaymentMethodById(
                java.util.UUID.fromString(paymentMethodId), updatePaymentMethodCommand);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{paymentMethodId}")
    public Mono<ResponseEntity<Object>> deletePaymentMethodById(@PathVariable @UUID String paymentMethodId) {
        return paymentMethodService.deletePaymentMethodById(java.util.UUID.fromString(paymentMethodId));
    }
}
