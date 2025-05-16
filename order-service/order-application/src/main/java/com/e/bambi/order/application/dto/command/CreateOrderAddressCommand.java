package com.e.bambi.order.application.dto.command;

import com.e.bambi.shared.kernel.application.bus.Command;
import lombok.Builder;
import lombok.Getter;
import reactor.core.publisher.Mono;

@Getter
@Builder
public class CreateOrderAddressCommand implements Command<Mono<Void>> {
    private String country;
    private String address;
    private String city;
    private String province;
    private String postalCode;
    private String phoneNumber;
}
