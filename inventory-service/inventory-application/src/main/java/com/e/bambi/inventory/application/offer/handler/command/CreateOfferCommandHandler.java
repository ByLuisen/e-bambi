package com.e.bambi.inventory.application.offer.handler.command;

import com.e.bambi.inventory.application.offer.dto.command.CreateOfferCommand;
import com.e.bambi.inventory.application.offer.dto.response.OfferResponse;
import com.e.bambi.inventory.application.offer.mapper.OfferApplicationMapper;
import com.e.bambi.inventory.application.offer.port.outbound.repository.OfferRepository;
import com.e.bambi.inventory.domain.offer.entity.Offer;
import com.e.bambi.shared.kernel.application.bus.CommandHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateOfferCommandHandler implements CommandHandler<Mono<OfferResponse>, CreateOfferCommand> {

    private final OfferApplicationMapper offerApplicationMapper;
    private final OfferRepository offerRepository;

    @Override
    public Mono<OfferResponse> handle(CreateOfferCommand command) {
        Offer offer = offerApplicationMapper.createOfferCommandToOffer(command);
        offer.initializeOffer();
        return offerRepository.insert(offer)
                .onErrorMap(DuplicateKeyException.class, e ->
                        new DuplicateKeyException("The supplier already has an offer for the product")
                ).onErrorMap(Exception.class, e ->
                        new RuntimeException(e.getMessage())
                )
                .map(offerApplicationMapper::toOfferResponse);
    }
}