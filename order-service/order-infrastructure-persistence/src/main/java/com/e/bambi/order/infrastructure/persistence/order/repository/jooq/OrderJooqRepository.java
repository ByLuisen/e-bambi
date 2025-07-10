package com.e.bambi.order.infrastructure.persistence.order.repository.jooq;

import com.e.bambi.order.application.order.dto.query.OrderQuery;
import com.e.bambi.order.application.order.dto.response.ordersummary.OrderSummaryReadResponse;
import com.e.bambi.order.application.order.dto.response.orderwithdetails.OrderWithDetailReadResponse;
import com.e.bambi.order.application.order.dto.response.PaginatedResultResponse;
import com.e.bambi.order.domain.exception.OrderNotFoundException;
import com.e.bambi.order.domain.order.entity.Order;
import com.e.bambi.order.infrastructure.persistence.order.entity.OrderEntity;
import com.e.bambi.order.infrastructure.persistence.order.mapper.OrderPersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.e.bambi.order.infrastructure.persistence.jooq.Tables.ORDERS;
import static com.e.bambi.order.infrastructure.persistence.jooq.Tables.ORDER_ITEMS;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.multiset;

@Repository
@RequiredArgsConstructor
public class OrderJooqRepository {

    private final DSLContext dslContext;
    private final OrderPersistenceMapper orderPersistenceMapper;

    public Mono<Order> findOrderWithItems(UUID orderId) {
        Condition where = ORDERS.ID.eq(orderId);

        return Mono.from(getOrderWithItemsQuery(where))
                .map(orderPersistenceMapper::toOrderWithItems);
    }

    public Mono<PaginatedResultResponse<OrderSummaryReadResponse>> findByUserId(UUID userId, int page, Integer date) {
        var o = ORDERS;

        Condition where = o.USER_ID.eq(userId)
                .and(buildDate(date));
        SortField<?> sort = o.field(o.CREATED_AT.getName(), Object.class).desc();
        int limit = 10;
        int offset = limit * page;

        var countSql = dslContext.select(field("count(*)", SQLDataType.BIGINT))
                .from(o)
                .where(where);

        return Mono.zip(
                Flux.from(getOrderSummaryQuery(where)
                                .orderBy(sort)
                                .offset(offset)
                                .limit(limit))
                        .map(orderPersistenceMapper::toOrderSummaryReadResponse)
                        .collectList(),
                Mono.from(countSql)
                        .map(Record1::value1)
        ).flatMap(tuple -> {
            if (tuple.getT1().isEmpty()) {
                return Mono.error(new OrderNotFoundException("No orders could be found"));
            }
            return Mono.just(new PaginatedResultResponse<>(tuple.getT1(), tuple.getT2()));
        });

    }

    public Mono<OrderWithDetailReadResponse> findByUserIdAndOrderId(UUID userId, UUID orderId) {
        Condition where = ORDERS.USER_ID.eq(userId)
                .and(ORDERS.ID.eq(orderId));

        return Mono.from(getOrderWithDetailsQuery(where))
                .map(orderPersistenceMapper::toOrderWithDetailsReadResponse);
    }

    public Mono<PaginatedResultResponse<OrderSummaryReadResponse>> findAll(OrderQuery filters) {
        var o = ORDERS;

        Condition where = buildWhere(filters);
        SortField<?> sort = buildSort(filters.getOrderBy());
        int limit = 10;
        int offset = limit * filters.getPage();

        var countSql = dslContext.select(field("count(*)", SQLDataType.BIGINT))
                .from(o)
                .where(where);

        return Mono.zip(
                Flux.from(getOrderSummaryQuery(where)
                                .orderBy(sort)
                                .offset(offset)
                                .limit(limit))
                        .map(orderPersistenceMapper::toOrderSummaryReadResponse)
                        .collectList(),
                Mono.from(countSql)
                        .map(Record1::value1)
        ).flatMap(tuple -> {
            if (tuple.getT1().isEmpty()) {
                return Mono.error(new OrderNotFoundException("No orders could be found"));
            }
            return Mono.just(new PaginatedResultResponse<>(tuple.getT1(), tuple.getT2()));
        });
    }

    public Mono<OrderWithDetailReadResponse> findByOrderId(UUID orderId) {
        Condition where = ORDERS.ID.eq(orderId);

        return Mono.from(getOrderWithDetailsQuery(where))
                .map(orderPersistenceMapper::toOrderWithDetailsReadResponse);
    }

    private Condition buildDate(Integer date) {
        OffsetDateTime minDate;
        OffsetDateTime maxDate;
        switch (date) {
            case 30 -> {
                minDate = OffsetDateTime.now().minusDays(30);
                maxDate = OffsetDateTime.now();
            }
            case 3 -> {
                minDate = OffsetDateTime.now().minusMonths(3);
                maxDate = OffsetDateTime.now();
            }
            default -> {
                ZoneOffset offset = ZoneOffset.of("Z");

                minDate = OffsetDateTime.of(
                        date, 1, 1,
                        0, 0, 0, 0,
                        offset
                );

                maxDate = OffsetDateTime.of(
                        date, 12, 31,
                        23, 59, 59, 999_999_999,
                        offset
                );
            }
        }

        return ORDERS.CREATED_AT.between(minDate, maxDate);
    }

    private Condition buildWhere(OrderQuery q) {
        List<Condition> conditions = new ArrayList<>();
        if (q.getPaymentMethodId() != null) {
            conditions.add(ORDERS.PAYMENT_METHOD_ID.in(q.getPaymentMethodId()));
        }
        if (q.getUserId() != null) {
            conditions.add(ORDERS.USER_ID.in(q.getUserId()));
        }
        if (q.getCreatedAt() != null) {
            conditions.add(ORDERS.CREATED_AT.between(
                    q.getCreatedAt().getFirst(),
                    q.getCreatedAt().getLast()
            ));
        }
        if (q.getTotalPrice() != null) {
            conditions.add(ORDERS.TOTAL_PRICE.between(
                    q.getTotalPrice().getFirst(),
                    q.getTotalPrice().getLast()
            ));
        }
        return conditions.isEmpty()
                ? DSL.noCondition()
                : DSL.and(conditions);
    }

    private SortField<?> buildSort(String orderBy) {
        String[] parts = orderBy.split("-");
        Field<?> field = ORDERS.field(parts[0], Object.class);
        return parts[1].equalsIgnoreCase("asc")
                ? field.asc()
                : field.desc();
    }

    private SelectConditionStep<?> getOrderSummaryQuery(Condition where) {
        var o = ORDERS;
        var oi = ORDER_ITEMS;

        var orderItemsSubquery = dslContext
                .select(
                        oi.IMAGE_URL.as("image_url"),
                        oi.PRODUCT_ID.as("product_id"),
                        oi.NAME.as("product_name")
                )
                .from(oi)
                .where(oi.ORDER_ID.eq(o.ID));

        return dslContext
                .select(
                        o.ID.as("id"),
                        o.ORDER_STATUS.as("order_status"),
                        o.COUNTRY.as("shipping_country"),
                        o.ADDRESS.as("shipping_address"),
                        o.CITY.as("shipping_city"),
                        o.PROVINCE.as("shipping_province"),
                        o.POSTAL_CODE.as("shipping_postal_code"),
                        o.PHONE_NUMBER.as("shipping_phone_number"),
                        o.TOTAL_PRICE.as("total_price"),
                        o.CREATED_AT.as("created_at"),
                        multiset(orderItemsSubquery).as("order_items")
                )
                .from(o)
                .where(where != null ? where : DSL.trueCondition());
    }

    private SelectConditionStep<?> getOrderWithDetailsQuery(Condition where) {
        var o = ORDERS;
        var oi = ORDER_ITEMS;

        var orderItemsSubquery = dslContext
                .select(
                        oi.IMAGE_URL.as("image_url"),
                        oi.SUPPLIER_ID.as("supplier_id"),
                        oi.SUPPLIER.as("supplier"),
                        oi.PRODUCT_ID.as("product_id"),
                        oi.NAME.as("product_name"),
                        oi.PRICE.as("price"),
                        oi.QUANTITY.as("quantity"),
                        oi.TOTAL_PRICE.as("item_total_price")
                ).from(oi)
                .where(oi.ORDER_ID.eq(o.ID));

        return dslContext
                .select(
                        o.ORDER_STATUS.as("order_status"),
                        o.PAYMENT_METHOD_ID.as("payment_method_id"),
                        o.PAYMENT_METHOD.as("payment_method"),
                        o.COUNTRY.as("shipping_country"),
                        o.ADDRESS.as("shipping_address"),
                        o.CITY.as("shipping_city"),
                        o.PROVINCE.as("shipping_province"),
                        o.POSTAL_CODE.as("shipping_postal_code"),
                        o.PHONE_NUMBER.as("shipping_phone_number"),
                        o.TOTAL_PRICE.as("order_total_price"),
                        o.CREATED_AT.as("created_at"),
                        multiset(orderItemsSubquery).as("order_items")
                ).from(o)
                .where(where != null ? where : DSL.trueCondition());
    }

    private SelectConditionStep<?> getOrderWithItemsQuery(Condition where) {
        var o = ORDERS;
        var oi = ORDER_ITEMS;

        var orderItemSubquery = dslContext
                .select(
                        oi.PRODUCT_ID.as("product_id"),
                        oi.SUPPLIER_ID.as("supplier_id"),
                        oi.QUANTITY.as("quantity")
                ).from(oi)
                .where(oi.ORDER_ID.eq(o.ID));

        return dslContext
                .select(
                        o.ID.as("id"),
                        o.ORDER_STATUS.as("order_status"),
                        o.PAYMENT_METHOD_ID.as("payment_method_id"),
                        o.FAILURE_MESSAGES.as("failure_messages"),
                        multiset(orderItemSubquery).as("order_items")
                ).from(o)
                .where(where != null ? where : DSL.trueCondition());
    }
}
