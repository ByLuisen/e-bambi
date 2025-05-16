package com.commerce.inventory_service.controller;

import com.commerce.inventory_service.domain.Department;
import com.commerce.inventory_service.dto.DepartmentInputDTO;
import com.commerce.inventory_service.service.DepartmentService;
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

    private final DepartmentService departmentService;

    @GetMapping
    public Flux<Department> findAllDepartments() {
        return departmentService.findAllDepartments();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ResponseEntity<Object>> saveDepartment(@RequestBody @Valid DepartmentInputDTO departmentInputDTO) {
        return departmentService.saveDepartment(departmentInputDTO);
    }

    @PutMapping("/{departmentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ResponseEntity<Object>> updateDepartment(@PathVariable @UUID String departmentId,
                                                         @RequestBody @Valid DepartmentInputDTO departmentInputDTO) {
        return departmentService.updateDepartment(java.util.UUID.fromString(departmentId), departmentInputDTO);
    }

    @DeleteMapping("/{departmentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ResponseEntity<Object>> deleteDepartmentById(@PathVariable @UUID String departmentId) {
        return departmentService.deleteDepartmentById(java.util.UUID.fromString(departmentId));
    }
}
