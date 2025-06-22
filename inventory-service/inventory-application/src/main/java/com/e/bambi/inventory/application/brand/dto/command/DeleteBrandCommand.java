package com.e.bambi.inventory.application.brand.dto.command;

import com.e.bambi.inventory.domain.shared.valueobject.BrandId;
import com.e.bambi.shared.kernel.application.bus.Command;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Getter
@RequiredArgsConstructor
public class DeleteBrandCommand extends Command<Mono<Void>> {
    private final BrandId brandId;
}
