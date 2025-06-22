package com.e.bambi.inventory.application.offer.mapper;

import com.e.bambi.inventory.application.offer.dto.command.CreateOfferCommand;
import com.e.bambi.inventory.application.offer.dto.command.UpdateOfferCommand;
import com.e.bambi.inventory.application.offer.dto.response.OfferResponse;
import com.e.bambi.inventory.domain.offer.entity.Offer;
import org.springframework.stereotype.Component;

@Component
public class OfferApplicationMapper {

    public Offer createOfferCommandToOffer(CreateOfferCommand command) {
        return Offer.builder()
                .supplierId(command.getSupplierId())
                .productId(command.getProductId())
                .price(command.getPrice())
                .stock(command.getStock())
                .build();
    }

    public Offer updateOfferCommandToOffer(UpdateOfferCommand command) {
        return Offer.builder()
                .offerId(command.getOfferId())
                .supplierId(command.getSupplierId())
                .productId(command.getProductId())
                .price(command.getPrice())
                .stock(command.getStock())
                .build();
    }

    public OfferResponse toOfferResponse(Offer offer) {
        return new OfferResponse(
                offer.getId().getValue(),
                offer.getSupplierId().getValue(),
                offer.getProductId().getValue(),
                offer.getPrice().getAmount(),
                offer.getStock().getQuantity()
        );
    }
}
