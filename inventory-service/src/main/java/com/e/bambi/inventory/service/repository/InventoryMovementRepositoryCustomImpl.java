package com.e.bambi.inventory.service.repository;

import com.e.bambi.inventory.service.dto.InventoryMovementFilterInputDTO;
import com.e.bambi.inventory.service.dto.InventoryMovementOutputDTO;
import com.e.bambi.inventory.service.dto.MovementTypeOutputDTO;
import com.e.bambi.inventory.service.dto.PaginatedResultOutputDTO;
import com.e.bambi.inventory.service.util.NamingUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.*;

import static com.e.bambi.inventory.service.jooq.Tables.INVENTORY_MOVEMENTS;
import static com.e.bambi.inventory.service.jooq.Tables.MOVEMENT_TYPES;
import static org.jooq.impl.DSL.field;

@Repository
@RequiredArgsConstructor
public class InventoryMovementRepositoryCustomImpl implements InventoryMovementRepositoryCustom {

    private final DSLContext dslContext;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    @Override
    public Mono<PaginatedResultOutputDTO> findInventoriesWithPaginationAndFilters(InventoryMovementFilterInputDTO filters) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        Map<String, Object> filtersMapped = objectMapper.convertValue(filters, Map.class);

        Condition where = DSL.trueCondition();

        var im = INVENTORY_MOVEMENTS;

        for (Map.Entry<String, Object> entry : filtersMapped.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (!key.equals("orderBy") && !key.equals("page") && !key.equals("size")) {
                String snakeCaseField = NamingUtil.toSnakeCase(key);
                String[] values = value.toString().split("\\|");
                List<Object> listOfValues = new ArrayList<>();

                if (key.equals("productSku")) {
                    listOfValues = Arrays.asList(values);
                } else {
                    for (String v : values) {
                        listOfValues.add(UUID.fromString(v));
                    }
                }
                where = where.and(im.field(snakeCaseField).in(listOfValues));
            }
        }

        val countSql = dslContext.select(field("count(*)", SQLDataType.BIGINT))
                .from(im)
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
                .map(it -> new PaginatedResultOutputDTO(it.getT1(), it.getT2()));
    }

    private SelectConditionStep<Record13<UUID, UUID, String, String, UUID,
            String, String, Integer, Integer, Integer, UUID, String, LocalDateTime>>
    getQuery(Condition where) {
        var im = INVENTORY_MOVEMENTS;
        var mt = MOVEMENT_TYPES;

        return dslContext
                .select(
                        im.ID, im.MOVEMENT_TYPE_ID, mt.NAME, mt.DESCRIPTION,
                        im.PRODUCT_ID, im.PRODUCT_SKU, im.PRODUCT_NAME,
                        im.QUANTITY, im.PREVIOUS_STOCK, im.NEW_STOCK, im.REFERENCE_ID,
                        im.REFERENCE_TABLE, im.CREATED_AT
                )
                .from(im)
                .join(mt).on(mt.ID.eq(im.MOVEMENT_TYPE_ID))
                .where(where != null ? where : DSL.trueCondition());
    }

    private InventoryMovementOutputDTO mapResult(Record13<UUID, UUID, String, String, UUID,
            String, String, Integer, Integer, Integer, UUID, String, LocalDateTime> record1) {
        return new InventoryMovementOutputDTO(
                record1.value1(),
                new MovementTypeOutputDTO(
                        record1.value2(), record1.value3(), record1.value4()
                ),
                record1.value5(), record1.value6(), record1.value7(), record1.value8(), record1.value9(), record1.value10(),
                record1.value11(), record1.value12(), record1.value13()
        );
    }
}
