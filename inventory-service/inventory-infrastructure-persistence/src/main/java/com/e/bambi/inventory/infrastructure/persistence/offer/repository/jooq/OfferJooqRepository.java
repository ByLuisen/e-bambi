package com.e.bambi.inventory.infrastructure.persistence.offer.repository.jooq;

import com.e.bambi.inventory.application.offer.dto.response.ProductOfferReadResponse;
import com.e.bambi.inventory.application.offer.dto.response.SupplierOfferReadResponse;
import com.e.bambi.inventory.application.shared.dto.response.PaginatedResultResponse;
import com.e.bambi.inventory.domain.exception.OfferNotFoundException;
import com.e.bambi.inventory.domain.shared.valueobject.Stock;
import com.e.bambi.inventory.infrastructure.persistence.offer.mapper.OfferPersistenceMapper;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static com.e.bambi.inventory.infrastructure.persistence.jooq.Tables.*;
import static org.jooq.impl.DSL.field;

@Repository
@RequiredArgsConstructor
public class OfferJooqRepository {

    private final DSLContext dslContext;
    private final OfferPersistenceMapper offerPersistenceMapper;

    public Mono<PaginatedResultResponse<SupplierOfferReadResponse>> findBySupplierId(UUID supplierId, int size, int page) {
        var o = OFFERS;
        Condition where = o.SUPPLIER_ID.eq(supplierId);
        int offset = page * size;

        val countSql = dslContext.select(field("count(*)", SQLDataType.BIGINT))
                .from(o)
                .where(where);

        return Mono.zip(
                        Flux.from(getFindBySupplierIdQuery(where)
                                        .limit(size)
                                        .offset(offset))
                                .map(offerPersistenceMapper::toSupplierOffer)
                                .collectList(),
                        Mono.from(countSql)
                                .map(Record1::value1)
                )
                .flatMap(it -> {
                    if (it.getT1().isEmpty()) {
                        return Mono.error(new OfferNotFoundException("Offers with supplier id: " + supplierId +
                                " could not be found"));
                    }
                    return Mono.just(new PaginatedResultResponse<>(it.getT1(), it.getT2()));
                });
    }

    public Flux<ProductOfferReadResponse> findByProductId(UUID productId) {
        Condition where = OFFERS.PRODUCT_ID.eq(productId);

        return Flux.from(getFindByProductIdQuery(where))
                .map(offerPersistenceMapper::toProductOffer);
    }

    public Mono<Stock> findOfferStock(UUID supplierId, UUID productId) {
        Condition where = OFFERS.SUPPLIER_ID.eq(supplierId)
                .and(OFFERS.PRODUCT_ID.eq(productId));

        return Mono.from(getFindOfferStock(where))
                .map(r ->
                        new Stock(r.get("stock", Integer.class))
                );
    }

    private SelectConditionStep<?> getFindBySupplierIdQuery(Condition where) {
        var o = OFFERS;
        var p = PRODUCTS;

        return dslContext.select(
                        p.ID.as("product_id"),
                        p.SKU.as("product_sku"),
                        p.NAME.as("product_name"),
                        o.STOCK.as("stock"),
                        o.PRICE.as("price")
                ).from(o)
                .join(p).on(p.ID.eq(o.PRODUCT_ID))
                .where(where != null ? where : DSL.trueCondition());
    }

    private SelectConditionStep<?> getFindByProductIdQuery(Condition where) {
        var s = SUPPLIERS;
        var o = OFFERS;

        return dslContext.select(
                        s.ID.as("supplier_id"),
                        s.NAME.as("supplier_name"),
                        o.STOCK.as("stock"),
                        o.PRICE.as("price")
                ).from(o)
                .join(s).on(s.ID.eq(o.SUPPLIER_ID))
                .where(where != null ? where : DSL.trueCondition());
    }

    private SelectConditionStep<?> getFindOfferStock(Condition where) {
        var o = OFFERS;

        return dslContext.select(
                        o.STOCK.as("stock")
                ).from(o)
                .where(where != null ? where : DSL.trueCondition());
    }
}
