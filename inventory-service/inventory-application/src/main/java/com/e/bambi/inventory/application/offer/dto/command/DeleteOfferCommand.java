package com.e.bambi.inventory.application.offer.dto.command;

import com.e.bambi.inventory.domain.offer.valueobject.OfferId;
import com.e.bambi.shared.kernel.domain.valueobject.SupplierId;
import com.e.bambi.shared.kernel.application.bus.Command;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Getter
@RequiredArgsConstructor
public class DeleteOfferCommand extends Command<Mono<Void>> {
    private final SupplierId supplierId;
    private final OfferId offerId;
}
