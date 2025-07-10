package com.e.bambi.inventory.infrastructure.rest.product.controller;

import com.e.bambi.inventory.application.product.dto.command.DeleteProductCommand;
import com.e.bambi.inventory.application.product.dto.query.ProductByIdQuery;
import com.e.bambi.inventory.application.product.dto.response.ProductResponse;
import com.e.bambi.inventory.application.product.dto.response.ProductSummaryReadResponse;
import com.e.bambi.inventory.application.product.dto.response.ProductWithDetailsReadResponse;
import com.e.bambi.inventory.application.shared.dto.response.PaginatedResultResponse;
import com.e.bambi.inventory.infrastructure.rest.product.dto.request.CreateProductRequestDto;
import com.e.bambi.inventory.infrastructure.rest.product.dto.request.ProductRequestDto;
import com.e.bambi.inventory.infrastructure.rest.product.dto.request.UpdateProductRequestDto;
import com.e.bambi.inventory.infrastructure.rest.product.mapper.ProductRestMapper;
import com.e.bambi.shared.kernel.application.port.inbound.bus.CommandBus;
import com.e.bambi.shared.kernel.application.port.inbound.bus.QueryBus;
import com.e.bambi.shared.kernel.domain.valueobject.ProductId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/products")
public class ProductController {

    private final QueryBus queryBus;
    private final CommandBus commandBus;
    private final ProductRestMapper productRestMapper;

    @GetMapping
    public Mono<ResponseEntity<PaginatedResultResponse<ProductSummaryReadResponse>>>
    findProducts(@Valid ProductRequestDto filters) {
        return queryBus.dispatch(productRestMapper.toProductQuery(filters))
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{productId}")
    public Mono<ResponseEntity<ProductWithDetailsReadResponse>> findProductWithDetails(@PathVariable @UUID
                                                                                       String productId) {
        return queryBus.dispatch(new ProductByIdQuery(
                new ProductId(java.util.UUID.fromString(productId))
        )).map(ResponseEntity::ok);
    }

    @PostMapping
    public Mono<ResponseEntity<ProductResponse>> saveProduct(@RequestBody @Valid
                                                             CreateProductRequestDto createProductRequestDto) {
        return commandBus.dispatch(productRestMapper
                .toCreateProductCommand(createProductRequestDto)
        ).map(ResponseEntity::ok);
    }

    @PutMapping("/{productId}")
    public Mono<ResponseEntity<ProductResponse>> updateProduct(@PathVariable @UUID String productId,
                                                                   @RequestBody @Valid
                                                                   UpdateProductRequestDto updateProductRequestDto) {
        return commandBus.dispatch(productRestMapper
                .toUpdateProductCommand(
                        productId,
                        updateProductRequestDto
                )
        ).map(ResponseEntity::ok);
    }

    @DeleteMapping("/{productId}")
    public Mono<ResponseEntity<Void>> deleteProduct(@PathVariable @UUID String productId) {
        return commandBus.dispatch(new DeleteProductCommand(
                new ProductId(java.util.UUID.fromString(productId))
        )).thenReturn(ResponseEntity.noContent().build());
    }
}

