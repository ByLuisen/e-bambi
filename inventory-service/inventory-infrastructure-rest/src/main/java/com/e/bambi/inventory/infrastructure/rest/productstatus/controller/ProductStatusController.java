package com.e.bambi.inventory.infrastructure.rest.productstatus.controller;

import com.e.bambi.inventory.application.productstatus.dto.query.ProductStatusFindAllQuery;
import com.e.bambi.inventory.application.productstatus.dto.response.ProductStatusResponse;
import com.e.bambi.shared.kernel.application.port.inbound.bus.QueryBus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/product-statuses")
public class ProductStatusController {

    private final QueryBus queryBus;

    public Flux<ProductStatusResponse> getProductStatuses() {
        return queryBus.dispatch(new ProductStatusFindAllQuery());
    }
}
