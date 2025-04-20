package com.e.bambi.order.service.security;

import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class SecurityUtils {

    public Mono<Jwt> getCurrentJwt() {
        return ReactiveSecurityContextHolder.getContext()
                .map(securityContext -> (Jwt) securityContext.getAuthentication().getPrincipal());
    }

    public Mono<UUID> getCurrentUserId() {
        return getCurrentJwt()
                .map(jwt -> UUID.fromString(jwt.getClaimAsString("sub")));
    }

    public Mono<String> getClaim(String claimName) {
        return getCurrentJwt()
                .map(jwt -> jwt.getClaimAsString(claimName));
    }
}