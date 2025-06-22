package com.e.bambi.inventory.application.department.dto.command;

import com.e.bambi.inventory.domain.shared.valueobject.DepartmentId;
import com.e.bambi.shared.kernel.application.bus.Command;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Getter
@RequiredArgsConstructor
public class DeleteDepartmentCommand extends Command<Mono<Void>> {
    private final DepartmentId departmentId;
}
