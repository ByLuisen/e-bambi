package com.e.bambi.shared.kernel.application.bus;

public interface CommandHandler<R, C extends Command<R>> {
    R handle(C command);
}
