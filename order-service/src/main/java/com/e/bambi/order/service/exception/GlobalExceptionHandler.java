package com.e.bambi.order.service.exception;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.file.AccessDeniedException;
import java.util.*;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler implements MessageSourceAware {

    @Nullable
    private MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    protected Mono<ResponseEntity<Object>> handleGenericException(Exception ex, ServerWebExchange exchange) {
        return handleExceptionInternal(ex, (Object) null, HttpHeaders.EMPTY, HttpStatusCode.valueOf(500), exchange);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public Mono<ResponseEntity<Object>> handleAccessDenied(AccessDeniedException ex, ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.ok(ex.getCause().toString()));
    }

    @ExceptionHandler(DuplicateKeyException.class)
    private Mono<ResponseEntity<Object>> handleDuplicateKey(DuplicateKeyException ex, ServerWebExchange exchange) {
        return handleExceptionInternal(ex, (Object) null, HttpHeaders.EMPTY, HttpStatusCode.valueOf(400), exchange);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    protected Mono<ResponseEntity<Object>> handleOrderNotFoundException(OrderNotFoundException ex, ServerWebExchange exchange) {
        return handleExceptionInternal(ex, (Object) null, HttpHeaders.EMPTY, HttpStatusCode.valueOf(404), exchange);
    }

    @ExceptionHandler(OrderItemNotFoundException.class)
    protected Mono<ResponseEntity<Object>> handleOrderItemNotFoundException(OrderItemNotFoundException ex, ServerWebExchange exchange) {
        return handleExceptionInternal(ex, (Object) null, HttpHeaders.EMPTY, HttpStatusCode.valueOf(404), exchange);
    }

    @ExceptionHandler(OrderStatusNotFoundException.class)
    protected Mono<ResponseEntity<Object>> handleOrderStatusNotFoundException(OrderStatusNotFoundException ex, ServerWebExchange exchange) {
        return handleExceptionInternal(ex, (Object) null, HttpHeaders.EMPTY, HttpStatusCode.valueOf(404), exchange);
    }

    @ExceptionHandler(OrderStatusHistoryNotFoundException.class)
    protected Mono<ResponseEntity<Object>> handleOrderStatusHistoryNotFoundException(OrderStatusHistoryNotFoundException ex, ServerWebExchange exchange) {
        return handleExceptionInternal(ex, (Object) null, HttpHeaders.EMPTY, HttpStatusCode.valueOf(404), exchange);
    }

    @ExceptionHandler(PaymentMethodNotFoundException.class)
    protected Mono<ResponseEntity<Object>> handlePaymentMethodNotFoundException(PaymentMethodNotFoundException ex, ServerWebExchange exchange) {
        return handleExceptionInternal(ex, (Object) null, HttpHeaders.EMPTY, HttpStatusCode.valueOf(404), exchange);
    }

    @Override
    protected Mono<ResponseEntity<Object>> handleHandlerMethodValidationException(HandlerMethodValidationException ex, HttpHeaders headers, HttpStatusCode status, ServerWebExchange exchange) {

        List<String> errors = null;

        if (ex.getDetailMessageArguments().length > 0) {
            errors = new ArrayList<>();
            for (Object error : ex.getDetailMessageArguments()) {
                errors.add(error.toString());
            }
        }

        return handleExceptionInternal(ex, errors, headers, status, exchange);
    }

    @Override
    protected Mono<ResponseEntity<Object>> handleWebExchangeBindException(WebExchangeBindException ex, HttpHeaders headers, HttpStatusCode status, ServerWebExchange exchange) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return handleExceptionInternal(ex, errors, headers, status, exchange);
    }

    @Override
    protected Mono<ResponseEntity<Object>> handleExceptionInternal(Exception ex, @Nullable Object errors, @Nullable HttpHeaders headers, HttpStatusCode status, ServerWebExchange exchange) {
        Object body;
        ErrorResponse errorResponse;

        if (exchange.getResponse().isCommitted()) {
            return Mono.error(ex);
        } else {
            if (ex instanceof ErrorResponse) {
                errorResponse = (ErrorResponse)ex;
            } else {
                errorResponse = ErrorResponse.builder(ex, ProblemDetail.forStatusAndDetail(status, ex.getMessage())).build();
            }

            body = errorResponse.updateAndGetBody(this.messageSource, getLocale(exchange));

            if (errors != null) {
                errorResponse.getBody().setProperty("errors", errors);
            }

            return this.createResponseEntity(body, headers, status, exchange);
        }
    }

    private static Locale getLocale(ServerWebExchange exchange) {
        Locale locale = exchange.getLocaleContext().getLocale();
        return locale != null ? locale : Locale.getDefault();
    }
}
