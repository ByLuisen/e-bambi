package com.e.bambi.inventory.application.product.mapper;

import com.e.bambi.inventory.application.product.dto.command.CreateProductCommand;
import com.e.bambi.inventory.application.product.dto.command.UpdateProductCommand;
import com.e.bambi.inventory.application.product.dto.response.ProductResponse;
import com.e.bambi.inventory.domain.product.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductApplicationMapper {

    public Product createProductCommandToProduct(CreateProductCommand command) {
        return Product.builder()
                .brandId(command.getBrandId())
                .departmentId(command.getDepartmentId())
                .productStatusId(command.getProductStatusId())
                .sku(command.getSku())
                .name(command.getName())
                .description(command.getDescription())
                .build();
    }

    public Product updateProductCommandToProduct(UpdateProductCommand command) {
        return Product.builder()
                .id(command.getProductId())
                .brandId(command.getBrandId())
                .departmentId(command.getDepartmentId())
                .productStatusId(command.getProductStatusId())
                .sku(command.getSku())
                .name(command.getName())
                .description(command.getDescription())
                .build();
    }

    public ProductResponse toProductResponse(Product product) {
        return new ProductResponse(
                product.getId().getValue(),
                product.getBrandId().getValue(),
                product.getDepartmentId().getValue(),
                product.getProductStatusId().getValue(),
                product.getSku(),
                product.getName(),
                product.getDescription()
        );
    }
}
