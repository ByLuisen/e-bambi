package com.e.bambi.shared.kernel.domain.event.publisher;

import com.e.bambi.shared.kernel.domain.event.DomainEvent;

public interface DomainEventPublisher<T extends DomainEvent> {
    void publish(T domainEvent);
}
