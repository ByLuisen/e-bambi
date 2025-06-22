package com.e.bambi.inventory.application.offer.dto.command;

import com.e.bambi.inventory.application.offer.dto.response.OfferResponse;
import com.e.bambi.inventory.domain.shared.valueobject.Stock;
import com.e.bambi.shared.kernel.domain.valueobject.SupplierId;
import com.e.bambi.shared.kernel.application.bus.Command;
import com.e.bambi.shared.kernel.domain.valueobject.Money;
import com.e.bambi.shared.kernel.domain.valueobject.ProductId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Getter
@RequiredArgsConstructor
public class CreateOfferCommand extends Command<Mono<OfferResponse>> {
    private final SupplierId supplierId;
    private final ProductId productId;
    private final Money price;
    private final Stock stock;
}
