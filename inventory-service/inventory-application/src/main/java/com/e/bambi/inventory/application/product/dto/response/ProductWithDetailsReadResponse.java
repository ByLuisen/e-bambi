package com.e.bambi.inventory.application.product.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ProductWithDetailsReadResponse {

    private final String sku;
    private final String name;
    private final String description;
    private final ProductWithDetailsBrandResponse brand;
    private final ProductWithDetailsDepartmentResponse department;
    private final String status;
    private final List<String> images;
}
