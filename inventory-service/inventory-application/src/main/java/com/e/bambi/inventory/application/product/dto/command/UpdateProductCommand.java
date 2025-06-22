package com.e.bambi.inventory.application.product.dto.command;

import com.e.bambi.inventory.application.product.dto.response.ProductResponse;
import com.e.bambi.inventory.domain.productstatus.valueobject.ProductStatusId;
import com.e.bambi.inventory.domain.shared.valueobject.BrandId;
import com.e.bambi.inventory.domain.shared.valueobject.DepartmentId;
import com.e.bambi.shared.kernel.application.bus.Command;
import com.e.bambi.shared.kernel.domain.valueobject.ProductId;
import lombok.Builder;
import lombok.Getter;
import reactor.core.publisher.Mono;

@Getter
@Builder
public class UpdateProductCommand extends Command<Mono<ProductResponse>> {
    private final ProductId productId;
    private final BrandId brandId;
    private final DepartmentId departmentId;
    private final ProductStatusId productStatusId;
    private final String sku;
    private final String name;
    private final String description;
}
