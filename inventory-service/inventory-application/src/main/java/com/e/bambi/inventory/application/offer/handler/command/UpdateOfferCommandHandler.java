package com.e.bambi.inventory.application.offer.handler.command;

import com.e.bambi.inventory.application.offer.dto.command.UpdateOfferCommand;
import com.e.bambi.inventory.application.offer.dto.response.OfferResponse;
import com.e.bambi.inventory.application.offer.mapper.OfferApplicationMapper;
import com.e.bambi.inventory.application.offer.port.outbound.repository.OfferRepository;
import com.e.bambi.inventory.domain.exception.OfferNotFoundException;
import com.e.bambi.shared.kernel.application.bus.CommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UpdateOfferCommandHandler implements CommandHandler<Mono<OfferResponse>, UpdateOfferCommand> {

    private final OfferRepository offerRepository;
    private final OfferApplicationMapper offerApplicationMapper;

    @Override
    public Mono<OfferResponse> handle(UpdateOfferCommand command) {
        return offerRepository.update(
                        offerApplicationMapper.updateOfferCommandToOffer(command)
                ).switchIfEmpty(
                        Mono.error(new OfferNotFoundException("Offer with id " +
                                command.getOfferId().getValue() + " could not be updated"))
                )
                .map(offerApplicationMapper::toOfferResponse);
    }
}
