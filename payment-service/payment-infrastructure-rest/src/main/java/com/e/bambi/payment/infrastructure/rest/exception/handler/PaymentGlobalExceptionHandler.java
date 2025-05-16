package com.e.bambi.payment.infrastructure.rest.exception.handler;

import com.e.bambi.payment.domain.exception.PaymentDomainException;
import com.e.bambi.payment.domain.exception.PaymentMethodNotFoundException;
import com.e.bambi.shared.infrastructure.rest.exception.handler.GlobalExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@ControllerAdvice
public class PaymentGlobalExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(PaymentDomainException.class)
    public Mono<ResponseEntity<Object>> handlePaymentDomainException(PaymentDomainException ex, ServerWebExchange exchange) {
        log.error(ex.getMessage(), ex);
        return handleExceptionInternal(ex, (Object) null, HttpHeaders.EMPTY, HttpStatusCode.valueOf(400), exchange);
    }

    @ExceptionHandler(PaymentMethodNotFoundException.class)
    public Mono<ResponseEntity<Object>> handlePaymentMethodNotFound(PaymentMethodNotFoundException ex, ServerWebExchange exchange) {
        log.error(ex.getMessage(), ex);
        return handleExceptionInternal(ex, (Object) null, HttpHeaders.EMPTY, HttpStatusCode.valueOf(404), exchange);
    }
}
