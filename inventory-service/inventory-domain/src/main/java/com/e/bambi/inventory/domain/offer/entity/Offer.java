package com.e.bambi.inventory.domain.offer.entity;

import com.e.bambi.inventory.domain.offer.valueobject.OfferId;
import com.e.bambi.inventory.domain.shared.valueobject.Stock;
import com.e.bambi.shared.kernel.domain.valueobject.SupplierId;
import com.e.bambi.shared.kernel.domain.entity.AggregateRoot;
import com.e.bambi.shared.kernel.domain.valueobject.Money;
import com.e.bambi.shared.kernel.domain.valueobject.ProductId;
import lombok.Getter;

import java.util.UUID;

@Getter
public class Offer extends AggregateRoot<OfferId> {

    private final SupplierId supplierId;
    private final ProductId productId;
    private final Money price;
    private final Stock stock;

    public void initializeOffer() {
        super.setId(new OfferId(UUID.randomUUID()));
    }

    private Offer(Builder builder) {
        super.setId(builder.offerId);
        supplierId = builder.supplierId;
        productId = builder.productId;
        price = builder.price;
        stock = builder.stock;
    }

    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {
        private OfferId offerId;
        private SupplierId supplierId;
        private ProductId productId;
        private Money price;
        private Stock stock;

        private Builder() {
        }

        public Builder offerId(OfferId val) {
            offerId = val;
            return this;
        }

        public Builder supplierId(SupplierId val) {
            supplierId = val;
            return this;
        }

        public Builder productId(ProductId val) {
            productId = val;
            return this;
        }

        public Builder price(Money val) {
            price = val;
            return this;
        }

        public Builder stock(Stock val) {
            stock = val;
            return this;
        }

        public Offer build() {
            return new Offer(this);
        }
    }
}
