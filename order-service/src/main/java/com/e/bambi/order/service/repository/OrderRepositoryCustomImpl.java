package com.e.bambi.order.service.repository;

import com.e.bambi.order.service.dto.queries.OrderQuery;
import com.e.bambi.order.service.dto.queries.PaginatedResultResponse;
import com.e.bambi.order.service.dto.queries.order.OrderAddress;
import com.e.bambi.order.service.dto.queries.order.OrderItem;
import com.e.bambi.order.service.dto.queries.order.OrderResponse;
import com.e.bambi.order.service.utils.NamingUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.jooq.*;
import org.jooq.Record;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.e.bambi.order.service.jooq.Tables.ORDERS;
import static com.e.bambi.order.service.jooq.Tables.ORDER_STATUSES;
import static com.e.bambi.order.service.jooq.Tables.PAYMENT_METHODS;
import static com.e.bambi.order.service.jooq.Tables.ORDER_ITEMS;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.multiset;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryCustomImpl implements OrderRepositoryCustom {

    private final DSLContext dslContext;

    @Override
    public Flux<OrderResponse> findOrdersByUserId(UUID userId) {

        return Flux.from(getQuery(ORDERS.USER_ID.eq(userId)))
                .map(this::mapResult);
    }

    @Override
    public Mono<OrderResponse> findOrderByUserIdAndOrderId(UUID userId, UUID orderId) {
        return Mono.from(getQuery(ORDERS.USER_ID.eq(userId)
                        .and(ORDERS.ID.eq(orderId))))
                .map(this::mapResult);
    }

    @Override
    public Mono<PaginatedResultResponse> findAllOrders(OrderQuery filters) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        Map<String, Object> mappedFilters = objectMapper.convertValue(filters, Map.class);

        Condition where = DSL.trueCondition();

        var o = ORDERS;

        for (Map.Entry<String, Object> filter : mappedFilters.entrySet()) {
            String key = filter.getKey();
            Object value = filter.getValue();

            if (!key.equals("orderBy") && !key.equals("page") && !key.equals("size")) {
                String snakeCaseField = NamingUtil.toSnakeCase(key);

                if (key.equals("totalPrice")) {
                    String[] prices = value.toString().split("-");
                    BigDecimal minPrice = new BigDecimal(prices[0]);
                    BigDecimal maxPrice = new BigDecimal(prices[1]);

                    where = where.and(o.TOTAL_PRICE.between(minPrice, maxPrice));
                } else if (key.equals("createdAt")) {
                    String[] dates = value.toString().split("\\|");
                    LocalDate minDate = LocalDate.parse(dates[0]);
                    LocalDate maxDate = LocalDate.parse(dates[1]);

                    LocalDateTime startOfDay = minDate.atStartOfDay();
                    LocalDateTime endOfDay = maxDate.atTime(LocalTime.MAX);

                    where = where.and(o.CREATED_AT.between(startOfDay, endOfDay));
                } else {
                    String[] values = value.toString().split("\\|");
                    List<Object> listOfValues = new ArrayList<>();

                    for (String v : values) {
                        listOfValues.add(UUID.fromString(v));
                    }

                    where = where.and(o.field(snakeCaseField).in(listOfValues));
                }
            }
        }

        val countSql = dslContext.select(field("count(*)", SQLDataType.BIGINT))
                .from(o)
                .where(where);

        int offset = filters.getPage() * filters.getSize();
        String[] orderBy = filters.getOrderBy().split("-");

        return Mono.zip(
                        Flux.from(getQuery(where)
                                        .orderBy(field(orderBy[0])
                                                .sort(SortOrder.valueOf(orderBy[1].toUpperCase())))
                                        .limit(offset, filters.getSize()))
                                .map(this::mapResult)
                                .collectList(),
                        Mono.from(countSql)
                                .map(Record1::value1)
                )
                .map(it -> new PaginatedResultResponse(it.getT1(), it.getT2()));
    }

    @Override
    public Mono<OrderResponse> findOrderByOrderId(UUID orderId) {

        return Mono.from(getQuery(ORDERS.ID.eq(orderId)))
                .map(this::mapResult);
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


    private OrderResponse mapResult(Record record) {
        @SuppressWarnings("unchecked")
        Result<Record> orderItems = record.get("order_items", Result.class);

        return new OrderResponse(
                record.get("order_id", UUID.class),
                record.get("user_id", UUID.class),
                record.get("status_id", UUID.class),
                record.get("status_name", String.class),
                new OrderAddress(
                        record.get("shipping_address", String.class),
                        record.get("shipping_country", String.class),
                        record.get("shipping_city", String.class),
                        record.get("shipping_province", String.class),
                        record.get("shipping_postal_code", String.class),
                        record.get("shipping_phone_number", String.class)
                ),
                orderItems.map(item -> new OrderItem(
                        item.get("order_id", UUID.class),
                        item.get("product_id", UUID.class),
                        item.get("product_sku", String.class),
                        item.get("product_name", String.class),
                        item.get("sold_by", String.class),
                        item.get("product_quantity", Integer.class),
                        item.get("product_price", BigDecimal.class),
                        item.get("total_price", BigDecimal.class),
                        item.get("discount", BigDecimal.class),
                        item.get("item_created_at", LocalDateTime.class)
                )),
                record.get("payment_method_name", String.class),
                record.get("order_total_price", BigDecimal.class),
                record.get("order_created_at", LocalDateTime.class),
                record.get("order_updated_at", LocalDateTime.class)
        );
    }
}
