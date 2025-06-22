package com.e.bambi.inventory.application.product.dto.command;

import com.e.bambi.shared.kernel.application.bus.Command;
import com.e.bambi.shared.kernel.domain.valueobject.ProductId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Getter
@RequiredArgsConstructor
public class DeleteProductCommand extends Command<Mono<Void>> {
    private final ProductId productId;
}
