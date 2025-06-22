package com.e.bambi.inventory.application.brand.dto.command;

import com.e.bambi.inventory.application.brand.dto.response.BrandResponse;
import com.e.bambi.inventory.domain.shared.valueobject.BrandId;
import com.e.bambi.shared.kernel.application.bus.Command;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Getter
@RequiredArgsConstructor
public class UpdateBrandCommand extends Command<Mono<BrandResponse>> {
    private final BrandId brandId;
    private final String name;
}
