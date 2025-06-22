package com.e.bambi.inventory.application.brand.dto.query;

import com.e.bambi.inventory.application.brand.dto.response.BrandResponse;
import com.e.bambi.shared.kernel.application.bus.Query;
import reactor.core.publisher.Flux;

public class BrandFindAllQuery extends Query<Flux<BrandResponse>> {
}
