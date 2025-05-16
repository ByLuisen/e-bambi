package com.e.bambi.order.infrastructure.persistence.order.repository.jooq;

import com.e.bambi.order.application.dto.response.OrderStatusHistoryResponse;
import lombok.RequiredArgsConstructor;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.e.bambi.order.service.dataaccess.order.jooq.Tables.ORDER_STATUSES;
import static com.e.bambi.order.service.dataaccess.order.jooq.Tables.ORDER_STATUS_HISTORY;
import static org.jooq.impl.DSL.multiset;
import static org.jooq.impl.DSL.select;

@Repository
@RequiredArgsConstructor
public class OrderStatusHistoryJooqRepository {

    private final DSLContext dslContext;

    public Flux<OrderStatusHistoryResponse> findByOrderId(UUID orderId) {

        return Flux.from(getQuery(ORDER_STATUS_HISTORY.ORDER_ID.eq(orderId)))
                .map(this::mapResult);
    }

    private SelectConditionStep<Record2<UUID,
                Result<Record3<String, String, LocalDateTime>>>>
    getQuery(Condition where) {

        var osh = ORDER_STATUS_HISTORY;
        var os = ORDER_STATUSES;

        return dslContext.select(
                        osh.ORDER_ID,
                        multiset(
                                select(
                                        os.NAME, osh.REASON, osh.CHANGED_AT
                                )
                                        .from(osh)
                                        .join(os).on(os.ID.eq(osh.ORDER_STATUS_ID))
                                        .where(where))
                ).from(osh)
                .where(where != null ? where : DSL.trueCondition());
    }

    private OrderStatusHistoryResponse mapResult(Record2<UUID,
            Result<Record3<String, String, LocalDateTime>>> record1) {
        return new OrderWithStatusHistoryResponse(record1.value1(), record1.value2().map(
                record2 -> new StatusHistoryResponse(record2.value1(), record2.value2(), record2.value3())));
    }
}
