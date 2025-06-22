package com.e.bambi.inventory.infrastructure.persistence.supplier.repository.jooq;

import com.e.bambi.inventory.application.shared.dto.response.PaginatedResultResponse;
import com.e.bambi.inventory.application.supplier.dto.response.SupplierResponse;
import com.e.bambi.inventory.domain.exception.SupplierNotFoundException;
import com.e.bambi.inventory.infrastructure.persistence.supplier.mapper.SupplierPersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.e.bambi.inventory.infrastructure.persistence.jooq.Tables.SUPPLIERS;
import static org.jooq.impl.DSL.field;

@Repository
@RequiredArgsConstructor
public class SupplierJooqRepository {

    private final DSLContext dslContext;
    private final SupplierPersistenceMapper supplierPersistenceMapper;

    public Mono<PaginatedResultResponse<SupplierResponse>> findAll(int size, int page) {
        Condition where = DSL.trueCondition();
        int offset = size * page;

        var countSql = dslContext.select(field("count(*)", SQLDataType.BIGINT))
                .from(SUPPLIERS);

        return Mono.zip(
                Flux.from(getQuery(where)
                                .limit(size)
                                .offset(offset))
                        .map(supplierPersistenceMapper::toSupplierResponse)
                        .collectList(),
                Mono.from(countSql)
                        .map(Record1::value1)
        ).flatMap(t -> {
            if (t.getT1().isEmpty()) {
                return Mono.error(new SupplierNotFoundException("No Suppliers could be found"));
            }
            return Mono.just(new PaginatedResultResponse<>(t.getT1(), t.getT2()));
        });
    }

    private SelectConditionStep<?> getQuery(Condition where) {
        var s = SUPPLIERS;

        return dslContext.select(
                        s.ID.as("supplier_id"),
                        s.NAME.as("supplier_name")
                ).from(s)
                .where(where != null ? where : DSL.trueCondition());
    }
}
