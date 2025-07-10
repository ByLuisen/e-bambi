package com.e.bambi.inventory.infrastructure.rest.brand.controller;

import com.e.bambi.inventory.application.brand.dto.command.CreateBrandCommand;
import com.e.bambi.inventory.application.brand.dto.command.DeleteBrandCommand;
import com.e.bambi.inventory.application.brand.dto.command.UpdateBrandCommand;
import com.e.bambi.inventory.application.brand.dto.query.BrandFindAllQuery;
import com.e.bambi.inventory.application.brand.dto.response.BrandResponse;
import com.e.bambi.inventory.domain.shared.valueobject.BrandId;
import com.e.bambi.inventory.infrastructure.rest.brand.dto.request.CreateBrandRequestDto;
import com.e.bambi.inventory.infrastructure.rest.brand.dto.request.UpdateBrandRequestDto;
import com.e.bambi.shared.kernel.application.port.inbound.bus.CommandBus;
import com.e.bambi.shared.kernel.application.port.inbound.bus.QueryBus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/brands")
public class BrandController {

    private final CommandBus commandBus;
    private final QueryBus queryBus;

    @GetMapping
    public Flux<BrandResponse> findAllBrands() {
        return queryBus.dispatch(new BrandFindAllQuery());
    }

    @PostMapping
    public Mono<ResponseEntity<BrandResponse>> createBrand(@RequestBody @Valid
                                                           CreateBrandRequestDto createBrandRequestDto) {
        return commandBus.dispatch(new CreateBrandCommand(
                createBrandRequestDto.getName()
        )).map(ResponseEntity::ok);
    }

    @PutMapping("/{brandId}")
    public Mono<ResponseEntity<BrandResponse>> updateBrand(@PathVariable @UUID
                                                           String brandId,
                                                           @RequestBody @Valid
                                                           UpdateBrandRequestDto updateBrandRequestDto) {
        return commandBus.dispatch(new UpdateBrandCommand(
                new BrandId(java.util.UUID.fromString(brandId)),
                updateBrandRequestDto.getName()
        )).map(ResponseEntity::ok);
    }

    @DeleteMapping("/{brandId}")
    public Mono<ResponseEntity<Void>> deleteBrandById(@PathVariable @UUID String brandId) {
        return commandBus.dispatch(new DeleteBrandCommand(
                        new BrandId(java.util.UUID.fromString(brandId))
                )
        ).thenReturn(ResponseEntity.noContent().build());
    }
}
