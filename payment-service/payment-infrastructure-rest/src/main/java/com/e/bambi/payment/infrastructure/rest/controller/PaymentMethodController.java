package com.e.bambi.payment.infrastructure.rest.controller;

import com.e.bambi.payment.application.dto.command.DeletePaymentMethodByIdCommand;
import com.e.bambi.payment.application.dto.query.PaymentMethodByIdQuery;
import com.e.bambi.payment.application.dto.query.PaymentMethodFindAllQuery;
import com.e.bambi.payment.application.dto.response.PaymentMethodResponse;
import com.e.bambi.payment.infrastructure.rest.dto.CreatePaymentMethodRequestDTO;
import com.e.bambi.payment.infrastructure.rest.mapper.PaymentMethodRestMapper;
import com.e.bambi.shared.kernel.application.port.inbound.bus.CommandBus;
import com.e.bambi.shared.kernel.application.port.inbound.bus.QueryBus;
import com.e.bambi.shared.kernel.domain.valueobject.PaymentMethodId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/v1/payment-methods")
@RequiredArgsConstructor
public class PaymentMethodController {

    private final QueryBus queryBus;
    private final CommandBus commandBus;
    private final PaymentMethodRestMapper paymentMethodRestMapper;

    @GetMapping
    public Flux<PaymentMethodResponse> getAllPaymentMethods() {
        return queryBus.dispatch(new PaymentMethodFindAllQuery());
    }

    @GetMapping("/{paymentMethodId}")
    public Mono<ResponseEntity<PaymentMethodResponse>> getPaymentMethodById(@PathVariable @UUID String paymentMethodId) {
        return queryBus.dispatch(new PaymentMethodByIdQuery(
                        new PaymentMethodId(java.util.UUID.fromString(paymentMethodId))
                ))
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ResponseEntity<PaymentMethodResponse>> createPaymentMethod(@RequestBody @Valid
                                                                           CreatePaymentMethodRequestDTO
                                                                                   createPaymentMethodRequestDTO) {
        return commandBus.dispatch(paymentMethodRestMapper
                        .toCreatePaymentMethodCommand(createPaymentMethodRequestDTO))
                .map(paymentMethodResponse ->
                        ResponseEntity
                                .created(URI.create("v1/payment-methods/" + paymentMethodResponse.id()))
                                .body(paymentMethodResponse)
                );
    }

    @DeleteMapping("/{paymentMethodId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ResponseEntity<Void>> deletePaymentMethodById(@PathVariable @UUID String paymentMethodId) {
        return commandBus.dispatch(new DeletePaymentMethodByIdCommand(
                        new PaymentMethodId(java.util.UUID.fromString(paymentMethodId))
                ))
                .thenReturn(ResponseEntity.noContent().build());
    }
}
