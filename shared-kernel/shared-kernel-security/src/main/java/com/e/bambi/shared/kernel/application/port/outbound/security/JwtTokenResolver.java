package com.e.bambi.shared.kernel.application.port.outbound.security;

import com.e.bambi.shared.kernel.domain.valueobject.UserId;
import reactor.core.publisher.Mono;

public interface JwtTokenResolver {

    Mono<UserId> getCurrentUserId();
    Mono<String> getClaim(String claimName);
}
