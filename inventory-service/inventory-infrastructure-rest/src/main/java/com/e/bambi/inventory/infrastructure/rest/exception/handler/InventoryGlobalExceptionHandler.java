package com.e.bambi.inventory.infrastructure.rest.exception.handler;

import com.e.bambi.shared.infrastructure.rest.exception.handler.GlobalExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class InventoryGlobalExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler()
}
