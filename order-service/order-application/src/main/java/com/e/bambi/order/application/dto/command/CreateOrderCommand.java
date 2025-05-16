package com.e.bambi.order.application.dto.command;

import com.e.bambi.order.application.dto.response.CreateOrderResponse;
import com.e.bambi.shared.kernel.domain.valueobject.Money;
import com.e.bambi.shared.kernel.application.bus.Command;
import com.e.bambi.shared.kernel.domain.valueobject.PaymentMethodId;
import lombok.Builder;
import lombok.Getter;
import reactor.core.publisher.Mono;

import java.util.List;

@Getter
@Builder
public class CreateOrderCommand implements Command<Mono<CreateOrderResponse>> {
    private PaymentMethodId paymentMethodId;
    private CreateOrderAddressCommand address;
    private List<CreateOrderItemCommand> items;
    private Money totalPrice;
}
