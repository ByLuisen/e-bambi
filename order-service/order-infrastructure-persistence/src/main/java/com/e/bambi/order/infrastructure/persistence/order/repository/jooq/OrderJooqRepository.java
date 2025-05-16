package com.e.bambi.order.infrastructure.persistence.order.repository.jooq;

import com.e.bambi.order.application.dto.query.OrderQuery;
import com.e.bambi.shared.kernel.domain.valueobject.OrderId;
import com.e.bambi.shared.kernel.domain.valueobject.UserId;
import lombok.RequiredArgsConstructor;
import org.jooq.*;
import org.jooq.Record;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static com.e.bambi.order.service.dataaccess.order.jooq.Tables.*;
import static org.jooq.impl.DSL.multiset;

@Repository
@RequiredArgsConstructor
public class OrderJooqRepository {

    private final DSLContext dslContext;

    public Flux<Record> findByUserId(UserId userId) {

        return Flux.from(getQuery(ORDERS.USER_ID.eq(userId.getValue())));
    }

    public Mono<Record> findByUserIdAndOrderId(UserId userId, OrderId orderId) {
        return Mono.from(getQuery(ORDERS.USER_ID.eq(userId.getValue())
                        .and(ORDERS.ID.eq(orderId.getValue()))));
    }

    public Flux<Record> findAllByFilter(OrderQuery filters) {

        Condition where = buildWhere(filters);
        SortField<?> sort = buildSort(filters.getOrderBy());

        int offset = filters.getPage() * filters.getSize();
        int limit = filters.getSize();

        return Flux.from(getQuery(where)
                .orderBy(sort)
                .limit(limit)
                .offset(offset));

    }

    public Mono<Record> findByOrderId(OrderId orderId) {

        return Mono.from(getQuery(ORDERS.ID.eq(orderId.getValue())));
    }

    private SelectConditionStep<?> getQuery(Condition where) {

        var o = ORDERS;
        var os = ORDER_STATUSES;
        var pm = PAYMENT_METHODS;
        var oi = ORDER_ITEMS;

        var orderItemSubquery = dslContext
                .select(
                        oi.ORDER_ID.as("order_id"),
                        oi.PRODUCT_ID.as("product_id"),
                        oi.SKU.as("product_sku"),
                        oi.NAME.as("product_name"),
                        oi.SOLD_BY.as("sold_by"),
                        oi.QUANTITY.as("product_quantity"),
                        oi.PRICE.as("product_price"),
                        oi.TOTAL_PRICE.as("total_price"),
                        oi.DISCOUNT.as("discount"),
                        oi.CREATED_AT.as("item_created_at")
                )
                .from(oi)
                .where(oi.ORDER_ID.eq(o.ID));

        return dslContext
                .select(
                        o.ID.as("order_id"),
                        o.USER_ID.as("user_id"),
                        o.TOTAL_PRICE.as("order_total_price"),
                        o.ADDRESS.as("shipping_address"),
                        o.COUNTRY.as("shipping_country"),
                        o.CITY.as("shipping_city"),
                        o.PROVINCE.as("shipping_province"),
                        o.POSTAL_CODE.as("shipping_postal_code"),
                        o.PHONE_NUMBER.as("shipping_phone_number"),
                        o.CREATED_AT.as("order_created_at"),
                        o.UPDATED_AT.as("order_updated_at"),
                        os.ID.as("status_id"),
                        os.NAME.as("status_name"),
                        pm.NAME.as("payment_method_name"),
                        multiset(orderItemSubquery).as("order_items")
                )
                .from(o)
                .join(os).on(os.ID.eq(o.STATUS_ID))
                .join(pm).on(pm.ID.eq(o.PAYMENT_METHOD_ID))
                .where(where != null ? where : DSL.trueCondition());
    }

    private Condition buildWhere(OrderQuery q) {
        Condition c = DSL.trueCondition();
        if (q.getStatusId() != null) {
            List<UUID> ids = parseUuids(q.getStatusId());
            c = c.and(ORDERS.STATUS_ID.in(ids));
        }
        if (q.getPaymentMethodId() != null) {
            List<UUID> ids = parseUuids(q.getPaymentMethodId());
            c = c.and(ORDERS.PAYMENT_METHOD_ID.in(ids));
        }
        if (q.getUserId() != null) {
            List<UUID> ids = parseUuids(q.getUserId());
            c = c.and(ORDERS.USER_ID.in(ids));
        }
        if (q.getCreatedAt() != null) {
            LocalDate[] dates = parseDateRange(q.getCreatedAt());
            c = c.and(ORDERS.CREATED_AT.between(
                    dates[0].atStartOfDay(),
                    dates[1].atTime(LocalTime.MAX)
            ));
        }
        if (q.getTotalPrice() != null) {
            BigDecimal[] prices = parseNumberRange(q.getTotalPrice());
            c = c.and(ORDERS.TOTAL_PRICE.between(prices[0], prices[1]));
        }
        return c;
    }

    private SortField<?> buildSort(String orderBy) {
        String[] parts = orderBy.split("-");
        Field<?> field = ORDERS.field(parts[0], Object.class);
        return parts[1].equalsIgnoreCase("asc")
                ? field.asc()
                : field.desc();
    }

    private List<UUID> parseUuids(String pipeSeparated) {
        return Stream.of(pipeSeparated.split("\\|"))
                .map(UUID::fromString)
                .toList();
    }

    private LocalDate[] parseDateRange(String pipeSeparated) {
        String[] d = pipeSeparated.split("\\|");
        return new LocalDate[]{LocalDate.parse(d[0]), LocalDate.parse(d[1])};
    }

    private BigDecimal[] parseNumberRange(String dashSeparated) {
        String[] p = dashSeparated.split("-");
        return new BigDecimal[]{new BigDecimal(p[0]), new BigDecimal(p[1])};
    }
}
