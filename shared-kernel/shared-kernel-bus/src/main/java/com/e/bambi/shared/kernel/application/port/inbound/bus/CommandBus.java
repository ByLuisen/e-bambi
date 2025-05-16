package com.e.bambi.shared.kernel.application.port.inbound.bus;

import com.e.bambi.shared.kernel.application.bus.Command;

public interface CommandBus {

    <R> R dispatch(Command<R> command);
}
