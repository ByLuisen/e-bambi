package com.e.bambi.order.application.order.dto.command.createorder;

import com.e.bambi.order.application.order.dto.response.CreateOrderResponse;
import com.e.bambi.shared.kernel.application.bus.Command;
import com.e.bambi.shared.kernel.domain.valueobject.Money;
import com.e.bambi.shared.kernel.domain.valueobject.UserId;
import lombok.Builder;
import lombok.Getter;
import reactor.core.publisher.Mono;

import java.util.List;

@Getter
@Builder
public class CreateOrderCommand extends Command<Mono<CreateOrderResponse>> {
    private final UserId userId;
    private final CreateOrderPaymentMethodCommand paymentMethod;
    private final CreateOrderAddressCommand address;
    private final List<CreateOrderItemCommand> items;
    private final Money totalPrice;
}
