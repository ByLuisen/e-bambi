package com.e.bambi.payment.infrastructure.persistence.mapper;

import com.e.bambi.payment.domain.entity.PaymentMethod;
import com.e.bambi.payment.infrastructure.persistence.entity.PaymentMethodEntity;
import com.e.bambi.shared.kernel.domain.valueobject.PaymentMethodId;
import org.springframework.stereotype.Component;

@Component
public class PaymentMethodPersistenceMapper {

    public PaymentMethod toPaymentMethod(PaymentMethodEntity paymentMethod) {
        return PaymentMethod.builder()
                .paymentMethodId(new PaymentMethodId(paymentMethod.getId()))
                .name(paymentMethod.getName())
                .description(paymentMethod.getDescription())
                .build();
    }

    public PaymentMethodEntity toPaymentMethodEntity(PaymentMethod paymentMethod) {
        return PaymentMethodEntity.builder()
                .name(paymentMethod.getName())
                .description(paymentMethod.getDescription())
                .build();
    }
}
