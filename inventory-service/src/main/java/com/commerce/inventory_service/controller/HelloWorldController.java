package com.commerce.inventory_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class HelloWorldController {

    @Value("${debug.enabled:false}")
    private boolean debugEnabled;

    private final Environment environment;

    @GetMapping("/debug")
    public Mono<ResponseEntity<String>> getDebugInfo() {
        if (!debugEnabled) {
            return Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN).body("Debugging is disabled"));
        }
        String port = environment.getProperty("local.server.port");
        String host = environment.getProperty("HOSTNAME");

        return Mono.just(ResponseEntity.ok("Hello World " + host + ":" + port));
    }
}