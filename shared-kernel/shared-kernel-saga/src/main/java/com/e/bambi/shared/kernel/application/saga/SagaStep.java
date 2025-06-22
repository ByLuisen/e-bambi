package com.e.bambi.shared.kernel.application.saga;

import reactor.core.publisher.Mono;

public interface SagaStep<S, E> {
    Mono<Void> process(S data);
    Mono<Void> rollback(E data);
}
