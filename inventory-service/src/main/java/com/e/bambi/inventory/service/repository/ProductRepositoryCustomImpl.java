package com.e.bambi.inventory.service.repository;

import com.e.bambi.inventory.service.dto.*;
import com.e.bambi.inventory.service.exception.ProductNotFoundException;
import com.e.bambi.inventory.service.util.NamingUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.jooq.*;
import org.jooq.Record;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.*;

import static com.e.bambi.inventory.service.jooq.Tables.*;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.multiset;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final DSLContext dslContext;

    @Override
    public Mono<PaginatedResultOutputDTO> findProductsWithPaginationAndFilters(ProductFilterInputDTO filters) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        Map<String, Object> filtersMapped = objectMapper.convertValue(filters, Map.class);

        Condition where = DSL.trueCondition();
        var p = PRODUCTS;

        for (Map.Entry<String, Object> entry : filtersMapped.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (!key.equals("orderBy") && !key.equals("page") && !key.equals("size")) {
                String snakeCaseField = NamingUtil.toSnakeCase(key);

                if (key.equals("price")) {
                    String[] prices = value.toString().split("-");
                    BigDecimal minPrice = new BigDecimal(prices[0]);
                    BigDecimal maxPrice = new BigDecimal(prices[1]);

                    where = where.and(p.PRICE.between(minPrice, maxPrice));
                } else {
                    String[] values = value.toString().split("\\|");
                    List<Object> listOfValues = new ArrayList<>();

                    if (key.equals("sku")) {
                        listOfValues = Arrays.asList(values);
                    } else {
                        for (String v : values) {
                            listOfValues.add(UUID.fromString(v));
                        }
                    }

                    where = where.and(p.field(snakeCaseField).in(listOfValues));
                }
            }
        }

        val countSQL = dslContext.select(field("count(*)", SQLDataType.BIGINT))
                .from(p)
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
                        Mono.from(countSQL)
                                .map(Record1::value1)
                )
                .flatMap(it -> {
                    if (!it.getT1().isEmpty()) {
                        return Mono.just(new PaginatedResultOutputDTO(it.getT1(), it.getT2()));
                    }
                    return Mono.error(new ProductNotFoundException("Product/s not found for the given filters."));

                });
    }

    @Override
    public Mono<ProductOutputDTO> findProductById(UUID productId) {
        return Mono.from(getQuery(PRODUCTS.ID.eq(productId)))
                .map(this::mapResult);
    }

    private SelectConditionStep<?> getQuery(Condition where) {

        var p = PRODUCTS;
        var i = IMAGES;
        var b = BRANDS;
        var d = DEPARTMENTS;
        var ps = PRODUCT_STATUSES;

        var imagesSubquery = dslContext
                .select(i.ID, i.URL_IMAGE)
                .from(i)
                .where(i.PRODUCT_ID.eq(p.ID));

        return dslContext
                .select(
                        p.ID.as("product_id"),
                        b.ID.as("brand_id"), b.NAME.as("brand_name"),
                        d.ID.as("department_id"), d.NAME.as("department_name"),
                        ps.ID.as("status_id"), ps.NAME.as("status_name"),
                        p.SKU, p.NAME, p.PRICE, p.DESCRIPTION, p.CREATED_AT, p.UPDATED_AT,
                        multiset(imagesSubquery).as("images")
                )
                .from(p)
                .join(b).on(b.ID.eq(p.BRAND_ID))
                .join(d).on(d.ID.eq(p.DEPARTMENT_ID))
                .join(ps).on(ps.ID.eq(p.PRODUCT_STATUS_ID))
                .where(where != null ? where : DSL.trueCondition());
    }

    private ProductOutputDTO mapResult(Record record) {
        @SuppressWarnings("unchecked")
        Result<Record2<UUID, String>> images = record.get("images", Result.class);

        return new ProductOutputDTO(
                record.get("product_id", UUID.class),
                new BrandOutputDTO(
                        record.get("brand_id", UUID.class),
                        record.get("brand_name", String.class)
                ),
                new DepartmentOutputDTO(
                        record.get("department_id", UUID.class),
                        record.get("department_name", String.class)
                ),
                new ProductStatusOutputDTO(
                        record.get("status_id", UUID.class),
                        record.get("status_name", String.class)
                ),
                record.get(PRODUCTS.SKU),
                record.get(PRODUCTS.NAME),
                record.get(PRODUCTS.PRICE),
                record.get(PRODUCTS.DESCRIPTION),
                record.get(PRODUCTS.CREATED_AT),
                record.get(PRODUCTS.UPDATED_AT),
                images.map(img -> new ImageOutputDTO(
                        img.get(IMAGES.ID),
                        img.get(IMAGES.URL_IMAGE)
                ))
        );
    }
}
