package com.e.bambi.inventory.infrastructure.persistence.offer.mapper;

import com.e.bambi.inventory.application.offer.dto.response.ProductOfferReadResponse;
import com.e.bambi.inventory.application.offer.dto.response.ProductOfferSupplierResponse;
import com.e.bambi.inventory.application.offer.dto.response.SupplierOfferProductResponse;
import com.e.bambi.inventory.application.offer.dto.response.SupplierOfferReadResponse;
import com.e.bambi.inventory.domain.offer.entity.Offer;
import com.e.bambi.inventory.domain.offer.valueobject.OfferId;
import com.e.bambi.inventory.domain.shared.valueobject.Stock;
import com.e.bambi.inventory.infrastructure.persistence.offer.entity.OfferEntity;
import com.e.bambi.shared.kernel.domain.valueobject.Money;
import com.e.bambi.shared.kernel.domain.valueobject.ProductId;
import com.e.bambi.shared.kernel.domain.valueobject.SupplierId;
import org.jooq.Record;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Component
public class OfferPersistenceMapper {

    public OfferEntity toOfferEntity(Offer offer) {
        return OfferEntity.builder()
                .id(offer.getId().getValue())
                .supplierId(offer.getSupplierId().getValue())
                .productId(offer.getProductId().getValue())
                .price(offer.getPrice().getAmount())
                .stock(offer.getStock().getQuantity())
                .version(offer.getVersion())
                .build();
    }

    public Offer toOffer(OfferEntity offerEntity) {
        return Offer.builder()
                .offerId(new OfferId(offerEntity.getId()))
                .supplierId(new SupplierId(offerEntity.getSupplierId()))
                .productId(new ProductId(offerEntity.getProductId()))
                .price(new Money(offerEntity.getPrice()))
                .stock(new Stock(offerEntity.getStock()))
                .version(offerEntity.getVersion())
                .build();
    }

    public SupplierOfferReadResponse toSupplierOffer(Record record) {
        return new SupplierOfferReadResponse(
                new SupplierOfferProductResponse(
                        record.get("product_id", UUID.class),
                        record.get("product_sku", String.class),
                        record.get("product_name", String.class)
                ),
                record.get("stock", Integer.class),
                record.get("price", BigDecimal.class)
        );
    }

    public ProductOfferReadResponse toProductOffer(Record r) {
        return new ProductOfferReadResponse(
                new ProductOfferSupplierResponse(
                        r.get("supplier_id", UUID.class),
                        r.get("supplier_name", String.class)
                ),
                r.get("stock", Integer.class),
                r.get("price", BigDecimal.class)
        );
    }

    public List<OfferEntity> toOfferEntities(List<Offer> offers) {
        return offers.stream()
                .map(offer ->
                        new OfferEntity(
                                offer.getId().getValue(),
                                offer.getSupplierId().getValue(),
                                offer.getProductId().getValue(),
                                offer.getPrice().getAmount(),
                                offer.getStock().getQuantity(),
                                offer.getVersion()
                        )).toList();
    }
}
