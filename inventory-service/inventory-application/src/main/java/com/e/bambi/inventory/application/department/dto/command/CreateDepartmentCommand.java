package com.e.bambi.inventory.application.department.dto.command;

import com.e.bambi.inventory.application.department.dto.response.DepartmentResponse;
import com.e.bambi.shared.kernel.application.bus.Command;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Getter
@RequiredArgsConstructor
public class CreateDepartmentCommand extends Command<Mono<DepartmentResponse>> {
    private final String name;
}
