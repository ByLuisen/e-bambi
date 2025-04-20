package com.e.bambi.inventory.service.service;

import com.e.bambi.inventory.service.model.Department;
import com.e.bambi.inventory.service.dto.DepartmentInputDTO;
import com.e.bambi.inventory.service.exception.DepartmentNotFoundException;
import com.e.bambi.inventory.service.mapper.DepartmentMapper;
import com.e.bambi.inventory.service.repository.DepartmentCrudRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentMapper departmentMapper;
    private final DepartmentCrudRepository departmentCrudRepository;

    public Flux<Department> findAllDepartments() {
        return departmentCrudRepository.findAll()
                .switchIfEmpty(Flux.error(new DepartmentNotFoundException("No department recorded.")));
    }

    public Mono<ResponseEntity<Object>> saveDepartment(DepartmentInputDTO departmentInputDTO) {
        Department department = departmentMapper.departmentInputDTOToDepartment(departmentInputDTO);

        return departmentCrudRepository.save(department)
                .map(saveDepartment -> {
                    String location = "/v1/departments/" + saveDepartment.getId();

                    return ResponseEntity.created(URI.create(location)).build();
                })
                .onErrorResume(DuplicateKeyException.class, e -> {
                    return Mono.error(new DuplicateKeyException("Duplicate entry detected. Please ensure the data is unique."));
                });
    }

    public Mono<ResponseEntity<Object>> updateDepartment(UUID departmentId, DepartmentInputDTO departmentInputDTO) {
        Department department = departmentMapper.departmentInputDTOToDepartment(departmentInputDTO);

        return departmentCrudRepository.findById(departmentId)
                .flatMap(departmentObtained -> {
                    department.setId(departmentId);
                    if (!departmentObtained.equals(department)) {
                        return departmentCrudRepository.save(department)
                                .thenReturn(ResponseEntity.noContent().build())
                                .onErrorResume(DuplicateKeyException.class, e -> {
                                    return Mono.error(new DuplicateKeyException("Duplicate entry detected. Please ensure the data is unique."));
                                });
                    }
                    return Mono.just(ResponseEntity.noContent().build());
                })
                .switchIfEmpty(Mono.error(new DepartmentNotFoundException("Department not found.")));
    }

    public Mono<ResponseEntity<Object>> deleteDepartmentById(UUID departmentId) {
        return departmentCrudRepository.existsById(departmentId)
                .flatMap(departmentExists-> {
                    if (departmentExists) {
                        return departmentCrudRepository.deleteById(departmentId)
                                .thenReturn(ResponseEntity.noContent().build());
                    }
                    return Mono.error(new DepartmentNotFoundException("Department not found."));
                });
    }
}
