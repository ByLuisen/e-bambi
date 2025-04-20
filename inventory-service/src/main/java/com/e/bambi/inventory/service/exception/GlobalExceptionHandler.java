package com.e.bambi.inventory.service.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.*;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler implements MessageSourceAware {
    @Nullable
    private MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    private Mono<ResponseEntity<Object>> handleGenericException(Exception ex, ServerWebExchange exchange) {
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

    @ExceptionHandler(InsufficientStockException.class)
    public Mono<ResponseEntity<Object>> handleInsufficientStockException(InsufficientStockException ex, ServerWebExchange exchange) {
        return handleExceptionInternal(ex, (Object) null, HttpHeaders.EMPTY, HttpStatusCode.valueOf(404), exchange);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    private Mono<ResponseEntity<Object>> handleProductNotFoundException(ProductNotFoundException ex, ServerWebExchange exchange) {
        return handleExceptionInternal(ex, (Object) null, HttpHeaders.EMPTY, HttpStatusCode.valueOf(404), exchange);
    }

    @ExceptionHandler(ProductBadRequestException.class)
    private Mono<ResponseEntity<Object>> handleProductBadRequestException(ProductBadRequestException ex, ServerWebExchange exchange) {
        return handleExceptionInternal(ex, ex.getDetailMessageArguments(), ex.getHeaders(), ex.getStatusCode(), exchange);
    }

    @ExceptionHandler(ProductStatusBadRequestException.class)
    private Mono<ResponseEntity<Object>> handleProductStatusBadRequestException(ProductStatusBadRequestException ex, ServerWebExchange exchange) {
        return handleExceptionInternal(ex, (Object) null, HttpHeaders.EMPTY, HttpStatusCode.valueOf(400), exchange);
    }

    @ExceptionHandler(MovementTypeNotFoundException.class)
    private Mono<ResponseEntity<Object>> handleMovementTypeNotFoundException(MovementTypeNotFoundException ex, ServerWebExchange exchange) {
        return handleExceptionInternal(ex, (Object) null, HttpHeaders.EMPTY, HttpStatusCode.valueOf(404), exchange);
    }

    @ExceptionHandler(ImageNotFoundException.class)
    private Mono<ResponseEntity<Object>> handleImageNotFoundException(ImageNotFoundException ex, ServerWebExchange exchange) {
        return handleExceptionInternal(ex, (Object) null, HttpHeaders.EMPTY, HttpStatusCode.valueOf(404), exchange);
    }

    @ExceptionHandler(BrandNotFoundException.class)
    private Mono<ResponseEntity<Object>> handleBrandNotFoundException(BrandNotFoundException ex, ServerWebExchange exchange) {
        return handleExceptionInternal(ex, (Object) null, HttpHeaders.EMPTY, HttpStatusCode.valueOf(404), exchange);
    }

    @ExceptionHandler(DepartmentNotFoundException.class)
    private Mono<ResponseEntity<Object>> handleDepartmentNotFoundException(DepartmentNotFoundException ex, ServerWebExchange exchange) {
        return handleExceptionInternal(ex, (Object) null, HttpHeaders.EMPTY, HttpStatusCode.valueOf(404), exchange);
    }

    @ExceptionHandler(ProductStatusNotFoundException.class)
    private Mono<ResponseEntity<Object>> handleProductStatusNotFoundException(ProductStatusNotFoundException ex, ServerWebExchange exchange) {
        return handleExceptionInternal(ex, (Object) null, HttpHeaders.EMPTY, HttpStatusCode.valueOf(404), exchange);
    }

    @ExceptionHandler(SupplierNotFoundException.class)
    private Mono<ResponseEntity<Object>> handleSupplierNotFoundException(SupplierNotFoundException ex, ServerWebExchange exchange) {
        return handleExceptionInternal(ex, (Object) null, HttpHeaders.EMPTY, HttpStatusCode.valueOf(404), exchange);
    }

    @ExceptionHandler(SupplierProductNotFoundException.class)
    private Mono<ResponseEntity<Object>> handleSupplierProductNotFoundException(SupplierProductNotFoundException ex, ServerWebExchange exchange) {
        return handleExceptionInternal(ex, (Object) null, HttpHeaders.EMPTY, HttpStatusCode.valueOf(404), exchange);
    }

    @ExceptionHandler(InventoryMovementNotFoundException.class)
    private Mono<ResponseEntity<Object>> handleInventoryMovementNotFoundException(InventoryMovementNotFoundException ex, ServerWebExchange exchange) {
        return handleExceptionInternal(ex, (Object) null, HttpHeaders.EMPTY, HttpStatusCode.valueOf(404), exchange);
    }

    @ExceptionHandler(InventoryMovementBadRequestException.class)
    private Mono<ResponseEntity<Object>> handleInventoryMovementBadRequestException(InventoryMovementBadRequestException ex, ServerWebExchange exchange) {
        return handleExceptionInternal(ex, ex.getDetailMessageArguments(), ex.getHeaders(), ex.getStatusCode(), exchange);
    }

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
                errorResponse = (ErrorResponse) ex;
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
