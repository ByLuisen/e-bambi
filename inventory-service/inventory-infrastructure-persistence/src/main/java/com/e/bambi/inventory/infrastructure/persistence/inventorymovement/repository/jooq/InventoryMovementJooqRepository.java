package com.e.bambi.inventory.infrastructure.persistence.inventorymovement.repository.jooq;

import com.e.bambi.inventory.application.inventorymovement.dto.query.InventoryMovementQuery;
import com.e.bambi.inventory.application.inventorymovement.dto.response.InventoryMovementSummaryReadResponse;
import com.e.bambi.inventory.application.shared.dto.response.PaginatedResultResponse;
import com.e.bambi.inventory.domain.exception.InventoryMovementNotFoundException;
import com.e.bambi.inventory.infrastructure.persistence.inventorymovement.mapper.InventoryMovementPersistenceMapper;
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

@Repository
@RequiredArgsConstructor
public class InventoryMovementJooqRepository {

    private final DSLContext dslContext;
    private final InventoryMovementPersistenceMapper inventoryMovementPersistenceMapper;

    public Mono<PaginatedResultResponse<InventoryMovementSummaryReadResponse>>
    searchInventoryMovements(InventoryMovementQuery query) {
        Condition where = buildWhere(query);
        SortField<?> sort = buildSort(query.getOrderBy());
        int offset = query.getSize() * query.getPage();
        int limit = query.getSize();

        val countSql = dslContext.select(field("count(*)", SQLDataType.BIGINT))
                .from(INVENTORY_MOVEMENTS)
                .where(where);

        return Mono.zip(
                Flux.from(getQuery(where)
                                .orderBy(sort)
                                .limit(limit)
                                .offset(offset))
                        .map(inventoryMovementPersistenceMapper::toInventoryMovementSummaryReadResponse)
                        .collectList(),
                Mono.from(countSql)
                        .map(Record1::value1)
        ).flatMap(t -> {
            if (t.getT1().isEmpty()) {
                return Mono.error(new InventoryMovementNotFoundException("Inventory movements could not be found"));
            }
            return Mono.just(new PaginatedResultResponse<>(t.getT1(), t.getT2()));
        });
    }

    public Mono<InventoryMovementSummaryReadResponse> findById(UUID inventoryMovementId) {
        Condition where = INVENTORY_MOVEMENTS.ID.eq(inventoryMovementId);

        return Mono.from(getQuery(where))
                .map(inventoryMovementPersistenceMapper::toInventoryMovementSummaryReadResponse);
    }

    private SelectConditionStep<?> getQuery(Condition where) {
        var im = INVENTORY_MOVEMENTS;
        var s = SUPPLIERS;
        var mt = MOVEMENT_TYPES;

        return dslContext.select(
                        im.ID.as("id"),
                        im.SUPPLIER_ID.as("supplier_id"), s.NAME.as("supplier_name"),
                        im.PRODUCT_ID.as("product_id"), im.PRODUCT_SKU.as("product_sku"),
                        im.PRODUCT_NAME.as("product_name"),
                        mt.NAME.as("movement_type"),
                        im.QUANTITY.as("quantity"), im.PREVIOUS_STOCK.as("previous_stock"),
                        im.NEW_STOCK.as("new_stock"), im.CREATED_AT.as("created_at")
                ).from(im)
                .join(s).on(s.ID.eq(im.SUPPLIER_ID))
                .join(mt).on(mt.ID.eq(im.MOVEMENT_TYPE_ID))
                .where(where != null ? where : DSL.trueCondition());

    }

    private Condition buildWhere(InventoryMovementQuery q) {
        var im = INVENTORY_MOVEMENTS;
        List<Condition> conditions = new ArrayList<>();
        if (q.getSupplierIds() != null && !q.getSupplierIds().isEmpty()) {
            conditions.add(im.SUPPLIER_ID.in(q.getSupplierIds()));
        }
        if (q.getProductIds() != null && !q.getProductIds().isEmpty()) {
            conditions.add(im.PRODUCT_ID.in(q.getProductIds()));
        }
        if (q.getMovementTypeIds() != null && !q.getMovementTypeIds().isEmpty()) {
            conditions.add(im.MOVEMENT_TYPE_ID.in(q.getMovementTypeIds()));
        }
        if (q.getProductSkus() != null && !q.getProductSkus().isEmpty()) {
            conditions.add(im.PRODUCT_SKU.in(q.getProductSkus()));
        }
        return conditions.isEmpty()
                ? DSL.noCondition()
                : DSL.and(conditions);
    }

    private SortField<?> buildSort(String orderBy) {
        String[] parts = orderBy.split("-");
        Field<?> field = INVENTORY_MOVEMENTS.field(parts[0], Object.class);
        return parts[1].equalsIgnoreCase("asc")
                ? field.asc()
                : field.desc();
    }
}
