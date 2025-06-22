package com.e.bambi.inventory.infrastructure.persistence.product.repository.jooq;

import com.e.bambi.inventory.application.product.dto.query.ProductQuery;
import com.e.bambi.inventory.application.product.dto.response.ProductSummaryReadResponse;
import com.e.bambi.inventory.application.product.dto.response.ProductWithDetailsReadResponse;
import com.e.bambi.inventory.application.shared.dto.response.PaginatedResultResponse;
import com.e.bambi.inventory.domain.exception.ProductNotFoundException;
import com.e.bambi.inventory.infrastructure.persistence.product.mapper.ProductPersistenceMapper;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.e.bambi.inventory.infrastructure.persistence.jooq.Tables.*;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.multiset;

@Repository
@RequiredArgsConstructor
public class ProductJooqRepository {

    private final DSLContext dslContext;
    private final ProductPersistenceMapper productPersistenceMapper;

    public Mono<PaginatedResultResponse<ProductSummaryReadResponse>> searchProductSummary(ProductQuery filters) {
        Condition where = buildWhere(filters);
        SortField<?> sort = buildSort(filters.getOrderBy());
        int offset = filters.getSize() * filters.getPage();
        int limit = filters.getSize();

        val countSql = dslContext.select(field("count(*)", SQLDataType.BIGINT))
                .from(PRODUCTS)
                .where(where);

        return Mono.zip(
                Flux.from(searchProductSummaryQuery(where)
                                .orderBy(sort)
                                .limit(limit)
                                .offset(offset))
                        .map(productPersistenceMapper::toProductSummaryReadResponse)
                        .collectList(),
                Mono.from(countSql)
                        .map(Record1::value1)
        ).flatMap(tuple -> {
            if (tuple.getT1().isEmpty()) {
                return Mono.error(new ProductNotFoundException("No Products could be found"));
            }
            return Mono.just(new PaginatedResultResponse<>(tuple.getT1(), tuple.getT2()));
        });
    }

    public Mono<ProductWithDetailsReadResponse> searchProductWithDetails(UUID productId) {
        var p = PRODUCTS;
        Condition where = p.ID.eq(productId);

        return Mono.from(searchProductWithDetailsQuery(where))
                .map(productPersistenceMapper::toProductWithDetailsReadResponse);
    }

    private Condition buildWhere(ProductQuery q) {
        var p = PRODUCTS;
        List<Condition> conditions = new ArrayList<>();
        if (q.getBrandIds() != null && !q.getBrandIds().isEmpty()) {
            conditions.add(p.BRAND_ID.in(q.getBrandIds()));
        }
        if (q.getDepartmentId() != null) {
            conditions.add(p.DEPARTMENT_ID.eq(q.getDepartmentId()));
        }
        if (q.getProductStatusIds() != null && !q.getProductStatusIds().isEmpty()) {
            conditions.add(p.PRODUCT_STATUS_ID.in(q.getProductStatusIds()));
        }
        if (q.getSkus() != null && !q.getSkus().isEmpty()) {
            conditions.add(p.SKU.in(q.getSkus()));
        }
        return conditions.isEmpty()
                ? DSL.noCondition()
                : DSL.and(conditions);
    }

    private SortField<?> buildSort(String orderBy) {
        String[] parts = orderBy.split("-");
        Field<?> field = PRODUCTS.field(parts[0], Object.class);
        return parts[1].equalsIgnoreCase("asc")
                ? field.asc()
                : field.desc();
    }

    private SelectConditionStep<?> searchProductSummaryQuery(Condition where) {
        var p = PRODUCTS;
        var b = BRANDS;
        var i = IMAGES;

        return dslContext.select(
                        p.ID.as("id"),
                        p.SKU.as("sku"),
                        p.NAME.as("name"),
                        p.CREATED_AT.as("created_at"),
                        b.NAME.as("brand"),
                        i.IMAGE_URL.as("image_url")
                ).from(p)
                .join(b).on(b.ID.eq(p.BRAND_ID))
                .join(i).on(i.PRODUCT_ID.eq(p.ID))
                .where(where != null ? where : DSL.trueCondition());
    }

    private SelectConditionStep<?> searchProductWithDetailsQuery(Condition where) {
        var p = PRODUCTS;
        var i = IMAGES;
        var b = BRANDS;
        var d = DEPARTMENTS;
        var ps = PRODUCT_STATUSES;

        var imageSubquery = dslContext
                .select(i.IMAGE_URL.as("image_url"))
                .from(i)
                .where(i.PRODUCT_ID.eq(p.ID));

        return dslContext.select(
                        p.SKU.as("product_sku"), p.NAME.as("product_name"),
                        p.DESCRIPTION.as("product_description"),
                        b.ID.as("brand_id"), b.NAME.as("brand_name"),
                        d.ID.as("department_id"), d.NAME.as("department_name"),
                        ps.NAME.as("product_status_name"),
                        multiset(imageSubquery).as("images")
                ).from(p)
                .join(b).on(b.ID.eq(p.BRAND_ID))
                .join(d).on(d.ID.eq(p.DEPARTMENT_ID))
                .join(ps).on(ps.ID.eq(p.PRODUCT_STATUS_ID))
                .where(where != null ? where : DSL.trueCondition());
    }

}
