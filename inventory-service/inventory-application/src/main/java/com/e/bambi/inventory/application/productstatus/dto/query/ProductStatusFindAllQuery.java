package com.e.bambi.inventory.application.productstatus.dto.query;

import com.e.bambi.inventory.application.productstatus.dto.response.ProductStatusResponse;
import com.e.bambi.shared.kernel.application.bus.Query;
import reactor.core.publisher.Flux;

public class ProductStatusFindAllQuery extends Query<Flux<ProductStatusResponse>> {
}
