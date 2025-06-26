package com.e.bambi.inventory.infrastructure.rest.exception.handler;

import com.e.bambi.inventory.domain.exception.*;
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
public class InventoryGlobalExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    private Mono<ResponseEntity<Object>> handleProductNotFoundException(ProductNotFoundException ex,
                                                                        ServerWebExchange exchange) {
        return handleExceptionInternal(ex, (Object) null, HttpHeaders.EMPTY, HttpStatusCode.valueOf(404), exchange);
    }

    @ExceptionHandler(ProductBadRequestException.class)
    private Mono<ResponseEntity<Object>> handleProductBadRequestException(ProductBadRequestException ex,
                                                                          ServerWebExchange exchange) {
        return handleExceptionInternal(ex, ex.getErrors(), HttpHeaders.EMPTY, HttpStatusCode.valueOf(400), exchange);
    }

    @ExceptionHandler(MovementTypeNotFoundException.class)
    private Mono<ResponseEntity<Object>> handleMovementTypeNotFoundException(MovementTypeNotFoundException ex,
                                                                             ServerWebExchange exchange) {
        return handleExceptionInternal(ex, (Object) null, HttpHeaders.EMPTY, HttpStatusCode.valueOf(404), exchange);
    }

    @ExceptionHandler(ImageNotFoundException.class)
    private Mono<ResponseEntity<Object>> handleImageNotFoundException(ImageNotFoundException ex,
                                                                      ServerWebExchange exchange) {
        return handleExceptionInternal(ex, (Object) null, HttpHeaders.EMPTY, HttpStatusCode.valueOf(404), exchange);
    }

    @ExceptionHandler(BrandNotFoundException.class)
    private Mono<ResponseEntity<Object>> handleBrandNotFoundException(BrandNotFoundException ex,
                                                                      ServerWebExchange exchange) {
        return handleExceptionInternal(ex, (Object) null, HttpHeaders.EMPTY, HttpStatusCode.valueOf(404), exchange);
    }

    @ExceptionHandler(DepartmentNotFoundException.class)
    private Mono<ResponseEntity<Object>> handleDepartmentNotFoundException(DepartmentNotFoundException ex,
                                                                           ServerWebExchange exchange) {
        return handleExceptionInternal(ex, (Object) null, HttpHeaders.EMPTY, HttpStatusCode.valueOf(404), exchange);
    }

    @ExceptionHandler(SupplierNotFoundException.class)
    private Mono<ResponseEntity<Object>> handleSupplierNotFoundException(SupplierNotFoundException ex,
                                                                         ServerWebExchange exchange) {
        return handleExceptionInternal(ex, (Object) null, HttpHeaders.EMPTY, HttpStatusCode.valueOf(404), exchange);
    }

    @ExceptionHandler(InventoryMovementNotFoundException.class)
    private Mono<ResponseEntity<Object>> handleInventoryMovementNotFoundException(InventoryMovementNotFoundException ex,
                                                                                  ServerWebExchange exchange) {
        return handleExceptionInternal(ex, (Object) null, HttpHeaders.EMPTY, HttpStatusCode.valueOf(404), exchange);
    }

    @ExceptionHandler(InventoryMovementBadRequestException.class)
    private Mono<ResponseEntity<Object>> handleInventoryMovementBadRequestException(InventoryMovementBadRequestException ex,
                                                                                    ServerWebExchange exchange) {
        return handleExceptionInternal(ex, ex.getErrors(), HttpHeaders.EMPTY, HttpStatusCode.valueOf(400), exchange);
    }
}
