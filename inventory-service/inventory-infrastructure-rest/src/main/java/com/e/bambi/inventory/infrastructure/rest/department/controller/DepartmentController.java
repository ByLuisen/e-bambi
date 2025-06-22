package com.e.bambi.inventory.infrastructure.rest.department.controller;

import com.e.bambi.inventory.application.department.dto.command.CreateDepartmentCommand;
import com.e.bambi.inventory.application.department.dto.command.DeleteDepartmentCommand;
import com.e.bambi.inventory.application.department.dto.command.UpdateDepartmentCommand;
import com.e.bambi.inventory.application.department.dto.query.DepartmentFindAllQuery;
import com.e.bambi.inventory.application.department.dto.response.DepartmentResponse;
import com.e.bambi.inventory.domain.shared.valueobject.DepartmentId;
import com.e.bambi.inventory.infrastructure.rest.department.dto.request.CreateDepartmentRequestDto;
import com.e.bambi.inventory.infrastructure.rest.department.dto.request.UpdateDepartmentRequestDto;
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
@RequestMapping("/v1/departments")
public class DepartmentController {

    private final QueryBus queryBus;
    private final CommandBus commandBus;

    @GetMapping
    public Flux<DepartmentResponse> findAllDepartments() {
        return queryBus.dispatch(new DepartmentFindAllQuery());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ResponseEntity<DepartmentResponse>> saveDepartment(@RequestBody @Valid
                                                                   CreateDepartmentRequestDto createDepartmentRequestDto) {
        return commandBus.dispatch(new CreateDepartmentCommand(
                createDepartmentRequestDto.getName()
        )).map(ResponseEntity::ok);
    }

    @PutMapping("/{departmentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ResponseEntity<DepartmentResponse>> updateDepartment(@PathVariable @UUID
                                                                     String departmentId,
                                                                     @RequestBody @Valid
                                                                     UpdateDepartmentRequestDto departmentInputDto) {
        return commandBus.dispatch(new UpdateDepartmentCommand(
                new DepartmentId(java.util.UUID.fromString(departmentId)),
                departmentInputDto.getName()
        )).map(ResponseEntity::ok);
    }

    @DeleteMapping("/{departmentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ResponseEntity<Object>> deleteDepartmentById(@PathVariable @UUID String departmentId) {
        return commandBus.dispatch(new DeleteDepartmentCommand(
                new DepartmentId(java.util.UUID.fromString(departmentId))
        )).thenReturn(ResponseEntity.noContent().build());
    }
}
