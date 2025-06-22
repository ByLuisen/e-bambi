package com.e.bambi.inventory.infrastructure.rest.offer.mapper;

import com.e.bambi.inventory.application.offer.dto.command.CreateOfferCommand;
import com.e.bambi.inventory.application.offer.dto.command.DeleteOfferCommand;
import com.e.bambi.inventory.application.offer.dto.command.UpdateOfferCommand;
import com.e.bambi.inventory.application.offer.dto.query.OfferBySupplierIdQuery;
import com.e.bambi.inventory.domain.offer.valueobject.OfferId;
import com.e.bambi.inventory.domain.shared.valueobject.Stock;
import com.e.bambi.shared.kernel.domain.valueobject.SupplierId;
import com.e.bambi.inventory.infrastructure.rest.offer.dto.request.CreateOfferRequestDto;
import com.e.bambi.inventory.infrastructure.rest.offer.dto.request.OfferBySupplierIdRequestDto;
import com.e.bambi.inventory.infrastructure.rest.offer.dto.request.UpdateOfferRequestDto;
import com.e.bambi.shared.kernel.domain.valueobject.Money;
import com.e.bambi.shared.kernel.domain.valueobject.ProductId;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OfferRestMapper {

    public OfferBySupplierIdQuery toOfferBySupplierIdQuery(String supplierId, OfferBySupplierIdRequestDto request) {
        return new OfferBySupplierIdQuery(
                new SupplierId(UUID.fromString(supplierId)),
                request.getSize(),
                request.getPage()
        );
    }

    public CreateOfferCommand toCreateOfferCommand(String supplierId, CreateOfferRequestDto request) {
        return new CreateOfferCommand(
                new SupplierId(UUID.fromString(supplierId)),
                new ProductId(UUID.fromString(request.getProductId())),
                new Money(request.getPrice()),
                new Stock(request.getStock())
        );
    }

    public UpdateOfferCommand toUpdateOfferCommand(String supplierId, String offerId, UpdateOfferRequestDto request) {
        return new UpdateOfferCommand(
                new OfferId(UUID.fromString(offerId)),
                new SupplierId(UUID.fromString(supplierId)),
                new ProductId(UUID.fromString(request.getProductId())),
                new Money(request.getPrice()),
                new Stock(request.getStock())
        );
    }

    public DeleteOfferCommand toDeleteOfferCommand(String supplierId, String offerId) {
        return new DeleteOfferCommand(
                new SupplierId(UUID.fromString(supplierId)),
                new OfferId(UUID.fromString(offerId))
        );
    }
}
