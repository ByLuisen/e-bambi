package com.e.bambi.inventory.application.offer.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public record OfferResponse(
        UUID offerId,
        UUID supplierId,
        UUID productId,
        BigDecimal price,
        Integer stock
) {

}
