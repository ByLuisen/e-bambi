package com.e.bambi.shared.kernel.application.bus;

public interface QueryHandler<R, Q extends Query<R>> {
   R handle(Q query);
}
