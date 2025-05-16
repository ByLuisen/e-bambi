package com.e.bambi.shared.kernel.application.saga;

import com.e.bambi.shared.kernel.domain.event.DomainEvent;

public interface SagaStep<T, S extends DomainEvent, U extends DomainEvent> {
    U process(T data);
    U rollback(T data);
}
