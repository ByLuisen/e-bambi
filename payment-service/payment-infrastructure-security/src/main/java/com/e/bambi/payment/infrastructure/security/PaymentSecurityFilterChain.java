package com.e.bambi.payment.infrastructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class PaymentSecurityFilterChain {

    @Bean
    SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http,
                                               Converter<Jwt, Mono<AbstractAuthenticationToken>> jwtAuthenticationConverter) {
        http.oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter)))
                .cors(Customizer.withDefaults())
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges ->
                        exchanges.pathMatchers(HttpMethod.GET,
                                        "/v*/payment-methods/**",
                                        "/v3/api-docs/**",
                                        "/openapi.yaml",
                                        "/swagger-ui.html",
                                        "/swagger-ui/**",
                                        "/debug",
                                        "/actuator/health/**"
                                )
                                .permitAll()
                                .pathMatchers("/v*/payment-methods/**")
                                .hasRole("ADMIN")
                                .anyExchange().authenticated()
                );

        return http.build();
    }
}
