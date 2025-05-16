package com.e.bambi.shared.infrastructure.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

@Configuration
public class JwtSecurityConfig {

    interface AuthoritiesConverter extends Converter<Map<String, Object>, Flux<GrantedAuthority>> {
    }

    @Bean
    AuthoritiesConverter realmRolesAuthoritiesConverter() {
        return claims -> {
            var realmAccess = (Map<String, Object>) claims.get("realm_access");
            if (realmAccess == null) {
                return Flux.empty();
            }
            var roles = (List<String>) realmAccess.get("roles");
            if (roles == null) {
                return Flux.empty();
            }
            return Flux.fromIterable(roles)
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role));
        };
    }


    @Bean
    ReactiveJwtAuthenticationConverter authenticationConverter(AuthoritiesConverter authoritiesConverter) {
        ReactiveJwtAuthenticationConverter jwtAuthenticationConverter = new ReactiveJwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> authoritiesConverter.convert(jwt.getClaims()));
        return jwtAuthenticationConverter;
    }
}

