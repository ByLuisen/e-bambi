package com.e.bambi.order.rest.exception.handler;

import com.e.bambi.shared.adapter.rest.exception.handler.GlobalExceptionHandler;
import com.e.bambi.order.domain.exception.*;
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
public class OrderGlobalExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(OrderDomainException.class)
    public Mono<ResponseEntity<Object>> handleOrderDomainException(OrderDomainException ex, ServerWebExchange exchange) {
        log.error(ex.getMessage(), ex);
        return handleExceptionInternal(ex, (Object) null, HttpHeaders.EMPTY, HttpStatusCode.valueOf(400), exchange);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    protected Mono<ResponseEntity<Object>> handleOrderNotFoundException(OrderNotFoundException ex,
                                                                        ServerWebExchange exchange) {
        log.error(ex.getMessage(), ex);
        return handleExceptionInternal(ex, (Object) null, HttpHeaders.EMPTY, HttpStatusCode.valueOf(404), exchange);
    }

    @ExceptionHandler(OrderStatusHistoryNotFoundException.class)
    protected Mono<ResponseEntity<Object>> handleOrderStatusHistoryNotFoundException(OrderStatusHistoryNotFoundException ex,
                                                                                     ServerWebExchange exchange) {
        log.error(ex.getMessage(), ex);
        return handleExceptionInternal(ex, (Object) null, HttpHeaders.EMPTY, HttpStatusCode.valueOf(404), exchange);
    }
}
