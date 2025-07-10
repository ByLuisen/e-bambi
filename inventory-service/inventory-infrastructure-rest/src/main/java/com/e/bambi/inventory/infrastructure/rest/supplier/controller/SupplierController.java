package com.e.bambi.inventory.infrastructure.rest.supplier.controller;

import com.e.bambi.inventory.application.shared.dto.response.PaginatedResultResponse;
import com.e.bambi.inventory.application.supplier.dto.command.DeleteSupplierCommand;
import com.e.bambi.inventory.application.supplier.dto.query.SupplierQuery;
import com.e.bambi.inventory.application.supplier.dto.response.SupplierResponse;
import com.e.bambi.shared.kernel.domain.valueobject.SupplierId;
import com.e.bambi.inventory.infrastructure.rest.supplier.dto.request.SupplierRequestDto;
import com.e.bambi.inventory.infrastructure.rest.supplier.dto.request.UpdateSupplierRequestDto;
import com.e.bambi.inventory.infrastructure.rest.supplier.mapper.SupplierRestMapper;
import com.e.bambi.shared.kernel.application.port.inbound.bus.CommandBus;
import com.e.bambi.shared.kernel.application.port.inbound.bus.QueryBus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/suppliers")
public class SupplierController {

    private final QueryBus queryBus;
    private final CommandBus commandBus;
    private final SupplierRestMapper supplierRestMapper;

    @GetMapping
    public Mono<ResponseEntity<PaginatedResultResponse<SupplierResponse>>> getAllSuppliers(@Valid
                                                                                               SupplierRequestDto
                                                                                                   supplierRequestDto) {
        return queryBus.dispatch(new SupplierQuery(
                        supplierRequestDto.getSize(),
                        supplierRequestDto.getPage()
                ))
                .map(ResponseEntity::ok);
    }

    @PutMapping("/me")
    public Mono<ResponseEntity<SupplierResponse>> updateSupplier(JwtAuthenticationToken auth,
                                                                 @RequestBody @Valid
                                                                 UpdateSupplierRequestDto updateSupplierRequestDTO) {
        return commandBus.dispatch(supplierRestMapper
                .toUpdateSupplierCommand(
                        auth.getToken().getClaimAsString("sub"),
                        updateSupplierRequestDTO
                )
        ).map(ResponseEntity::ok);
    }

    @DeleteMapping("/me")
    public Mono<ResponseEntity<Object>> deleteSupplierById(JwtAuthenticationToken auth) {
        return commandBus.dispatch(new DeleteSupplierCommand(
                new SupplierId(java.util.UUID.fromString(auth.getToken().getClaimAsString("sub")))
        )).thenReturn(ResponseEntity.notFound().build());
    }
}



