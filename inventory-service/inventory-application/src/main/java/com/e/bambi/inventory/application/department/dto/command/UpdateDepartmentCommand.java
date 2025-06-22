package com.e.bambi.inventory.application.department.dto.command;

import com.e.bambi.inventory.application.department.dto.response.DepartmentResponse;
import com.e.bambi.inventory.domain.shared.valueobject.DepartmentId;
import com.e.bambi.shared.kernel.application.bus.Command;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Getter
@RequiredArgsConstructor
public class UpdateDepartmentCommand extends Command<Mono<DepartmentResponse>> {
    private final DepartmentId departmentId;
    private final String name;
}
