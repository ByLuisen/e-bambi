package com.e.bambi.inventory.infrastructure.rest.offer.controller;

import com.e.bambi.inventory.application.offer.dto.query.OfferByProductIdQuery;
import com.e.bambi.inventory.application.offer.dto.response.OfferResponse;
import com.e.bambi.inventory.application.offer.dto.response.ProductOfferReadResponse;
import com.e.bambi.inventory.application.offer.dto.response.SupplierOfferReadResponse;
import com.e.bambi.inventory.application.shared.dto.response.PaginatedResultResponse;
import com.e.bambi.inventory.infrastructure.rest.offer.dto.request.CreateOfferRequestDto;
import com.e.bambi.inventory.infrastructure.rest.offer.dto.request.OfferBySupplierIdRequestDto;
import com.e.bambi.inventory.infrastructure.rest.offer.dto.request.UpdateOfferRequestDto;
import com.e.bambi.inventory.infrastructure.rest.offer.mapper.OfferRestMapper;
import com.e.bambi.shared.kernel.application.port.inbound.bus.CommandBus;
import com.e.bambi.shared.kernel.application.port.inbound.bus.QueryBus;
import com.e.bambi.shared.kernel.domain.valueobject.ProductId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class OfferController {

    private final QueryBus queryBus;
    private final CommandBus commandBus;
    private final OfferRestMapper offerRestMapper;

    @GetMapping("/suppliers/{supplierId}/offers")
    public Mono<ResponseEntity<PaginatedResultResponse<SupplierOfferReadResponse>>>
    getSupplierOffers(@PathVariable @UUID String supplierId,
                      @Valid OfferBySupplierIdRequestDto offerBySupplierIdRequestDto) {
        return queryBus.dispatch(offerRestMapper.toOfferBySupplierIdQuery(supplierId, offerBySupplierIdRequestDto))
                .map(ResponseEntity::ok);
    }

    @GetMapping("/products/{productId}/offers")
    public Flux<ProductOfferReadResponse> getProductOffers(@PathVariable @UUID String productId) {
        return queryBus.dispatch(new OfferByProductIdQuery(
                new ProductId(java.util.UUID.fromString(productId))
        ));
    }

    @PostMapping("/me/offers")
    @PreAuthorize("hasRole('SUPPLIER')")
    public Mono<ResponseEntity<OfferResponse>> createOffer(JwtAuthenticationToken auth,
                                                           @RequestBody @Valid
                                                           CreateOfferRequestDto createOfferRequestDto) {
        return commandBus.dispatch(offerRestMapper
                .toCreateOfferCommand(
                        auth.getToken().getClaimAsString("sub"),
                        createOfferRequestDto
                )
        ).map(ResponseEntity::ok);
    }

    @PutMapping("/me/offers/{offerId}")
    @PreAuthorize("hasRole('SUPPLIER')")
    public Mono<ResponseEntity<OfferResponse>> updateOffer(JwtAuthenticationToken auth,
                                                           @PathVariable @UUID String offerId,
                                                           @RequestBody @Valid
                                                           UpdateOfferRequestDto updateOfferRequestDto) {
        return commandBus.dispatch(offerRestMapper
                .toUpdateOfferCommand(
                        auth.getToken().getClaimAsString("sub"),
                        offerId,
                        updateOfferRequestDto
                )).map(ResponseEntity::ok);
    }

    @DeleteMapping("/me/offers/{offerId}")
    @PreAuthorize("hasRole('SUPPLIER')")
    public Mono<ResponseEntity<Void>> deleteOffer(JwtAuthenticationToken auth,
                                                  @PathVariable @UUID String offerId) {
        return commandBus.dispatch(
                offerRestMapper.toDeleteOfferCommand(
                        auth.getToken().getClaimAsString("sub"),
                        offerId
                )
        ).thenReturn(ResponseEntity.noContent().build());
    }
}
