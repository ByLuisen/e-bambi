package com.e.bambi.inventory.application.movementtype.dto.query;

import com.e.bambi.inventory.application.movementtype.dto.response.MovementTypeResponse;
import com.e.bambi.shared.kernel.application.bus.Query;
import reactor.core.publisher.Flux;

public class MovementTypeFindAllQuery extends Query<Flux<MovementTypeResponse>> {
}
