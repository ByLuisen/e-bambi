package com.e.bambi.inventory.infrastructure.rest.product.mapper;

import com.e.bambi.inventory.application.product.dto.command.CreateProductCommand;
import com.e.bambi.inventory.application.product.dto.command.UpdateProductCommand;
import com.e.bambi.inventory.application.product.dto.query.ProductQuery;
import com.e.bambi.inventory.domain.productstatus.valueobject.ProductStatusId;
import com.e.bambi.inventory.domain.shared.valueobject.BrandId;
import com.e.bambi.inventory.domain.shared.valueobject.DepartmentId;
import com.e.bambi.inventory.infrastructure.rest.product.dto.request.CreateProductRequestDto;
import com.e.bambi.inventory.infrastructure.rest.product.dto.request.ProductRequestDto;
import com.e.bambi.inventory.infrastructure.rest.product.dto.request.UpdateProductRequestDto;
import com.e.bambi.shared.kernel.domain.valueobject.ProductId;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
public class ProductRestMapper {

    public ProductQuery toProductQuery(ProductRequestDto request) {
        return new ProductQuery(
                convert(request.getBrandId(), UUID.class),
                request.getDepartmentId() != null ? UUID.fromString(request.getDepartmentId()) : null,
                convert(request.getProductStatusId(), UUID.class),
                convert(request.getSku(), String.class),
                request.getOrderBy(),
                request.getPage(),
                request.getSize()
        );
    }

    public CreateProductCommand toCreateProductCommand(CreateProductRequestDto request) {
        return CreateProductCommand.builder()
                .brandId(new BrandId(UUID.fromString(request.getBrandId())))
                .departmentId(new DepartmentId(UUID.fromString(request.getDepartmentId())))
                .productStatusId(new ProductStatusId(UUID.fromString(request.getProductStatusId())))
                .sku(request.getSku())
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }

    public UpdateProductCommand toUpdateProductCommand(String productId, UpdateProductRequestDto request) {
        return UpdateProductCommand.builder()
                .productId(new ProductId(UUID.fromString(productId)))
                .brandId(new BrandId(UUID.fromString(request.getBrandId())))
                .departmentId(new DepartmentId(UUID.fromString(request.getDepartmentId())))
                .productStatusId(new ProductStatusId(UUID.fromString(request.getProductStatusId())))
                .sku(request.getSku())
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }

    private <T> List<T> convert(String chain, Class<T> type) {
        return chain != null
                ? Arrays.stream(chain.split("\\|"))
                .map(item -> {
                    if (UUID.class.equals(type)) {
                        @SuppressWarnings("unchecked")
                        T value = (T) UUID.fromString(item);
                        return value;
                    }
                    @SuppressWarnings("unchecked")
                    T value = (T) item;
                    return value;
                }).toList()

                : null;
    }
}
