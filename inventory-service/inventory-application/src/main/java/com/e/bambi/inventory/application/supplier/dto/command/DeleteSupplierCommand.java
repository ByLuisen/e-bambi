package com.e.bambi.inventory.application.supplier.dto.command;

import com.e.bambi.shared.kernel.domain.valueobject.SupplierId;
import com.e.bambi.shared.kernel.application.bus.Command;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Getter
@RequiredArgsConstructor
public class DeleteSupplierCommand extends Command<Mono<Void>> {
    private final SupplierId supplierId;
}
