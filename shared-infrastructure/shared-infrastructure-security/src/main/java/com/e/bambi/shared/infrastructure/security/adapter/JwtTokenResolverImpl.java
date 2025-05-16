package com.e.bambi.shared.infrastructure.security.adapter;

import com.e.bambi.shared.kernel.application.port.outbound.security.JwtTokenResolver;
import com.e.bambi.shared.kernel.domain.valueobject.UserId;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class JwtTokenResolverImpl implements JwtTokenResolver {

    @Override
    public Mono<UserId> getCurrentUserId() {
        return getCurrentJwt()
                .map(jwt -> new UserId(UUID.fromString(jwt.getClaimAsString("sub"))));
    }

    @Override
    public Mono<String> getClaim(String claimName) {
        return getCurrentJwt()
                .map(jwt -> jwt.getClaimAsString(claimName));
    }

    private Mono<Jwt> getCurrentJwt() {
        return ReactiveSecurityContextHolder.getContext()
                .map(securityContext -> (Jwt) securityContext.getAuthentication().getPrincipal());
    }
}