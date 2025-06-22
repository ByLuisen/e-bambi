package com.e.bambi.inventory.application.department.dto.query;

import com.e.bambi.inventory.application.department.dto.response.DepartmentResponse;
import com.e.bambi.shared.kernel.application.bus.Query;
import reactor.core.publisher.Flux;

public class DepartmentFindAllQuery extends Query<Flux<DepartmentResponse>> {
}
