package com.e.bambi.payment.infrastructure.persistence.paymentmethod.mapper;

import com.e.bambi.payment.domain.paymentmethod.entity.PaymentMethod;
import com.e.bambi.payment.infrastructure.persistence.paymentmethod.entity.PaymentMethodEntity;
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
                .id(paymentMethod.getId().getValue())
                .name(paymentMethod.getName())
                .description(paymentMethod.getDescription())
                .build();
    }
}
