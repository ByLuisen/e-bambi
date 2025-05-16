package com.e.bambi.shared.kernel.application.port.inbound.bus;

import com.e.bambi.shared.kernel.application.bus.Query;

public interface QueryBus {
    <R> R dispatch(Query<R> query);
}
