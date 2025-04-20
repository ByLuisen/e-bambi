package com.e.bambi.order.service.dto.command.create.order;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UUID;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class CreateOrderCommand {

    @UUID
    @NotNull
    private String paymentMethodId;

    @Valid
    @NotNull
    private CreateOrderAddressCommand address;

    @Valid
    @NotNull
    private List<CreateOrderItemCommand> items;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false, message = "Total price must be greater than 0")
    private BigDecimal totalPrice;

    public java.util.UUID getPaymentMethodId() {
        return java.util.UUID.fromString(this.paymentMethodId);
    }
}
