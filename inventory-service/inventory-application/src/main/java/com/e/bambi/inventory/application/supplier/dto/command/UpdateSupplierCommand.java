package com.e.bambi.inventory.application.supplier.dto.command;

import com.e.bambi.inventory.application.supplier.dto.response.SupplierResponse;
import com.e.bambi.shared.kernel.domain.valueobject.SupplierId;
import com.e.bambi.shared.kernel.application.bus.Command;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Getter
@RequiredArgsConstructor
public class UpdateSupplierCommand extends Command<Mono<SupplierResponse>> {
    private final SupplierId supplierId;
    private final String name;
}
