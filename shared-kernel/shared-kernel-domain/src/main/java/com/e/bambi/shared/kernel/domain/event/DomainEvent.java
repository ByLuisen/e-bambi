package com.e.bambi.shared.kernel.domain.event;

public interface DomainEvent<T> {
    void fire();
}
