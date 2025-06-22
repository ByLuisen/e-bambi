package com.e.bambi.inventory.application.department.handler.command;

import com.e.bambi.inventory.application.department.dto.command.CreateDepartmentCommand;
import com.e.bambi.inventory.application.department.dto.response.DepartmentResponse;
import com.e.bambi.inventory.application.department.mapper.DepartmentApplicationMapper;
import com.e.bambi.inventory.application.department.port.outbound.repository.DepartmentRepository;
import com.e.bambi.inventory.domain.department.entity.Department;
import com.e.bambi.shared.kernel.application.bus.CommandHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateDepartmentCommandHandler implements CommandHandler<Mono<DepartmentResponse>, CreateDepartmentCommand> {

    private final DepartmentRepository departmentRepository;
    private final DepartmentApplicationMapper departmentApplicationMapper;

    @Override
    public Mono<DepartmentResponse> handle(CreateDepartmentCommand command) {
        Department department = departmentApplicationMapper.createDepartmentCommandToDepartment(command);
        department.initializeDepartment();
        return departmentRepository.insert(department)
                .onErrorMap(DuplicateKeyException.class, e -> {
                    log.error("Department with name: {} already exists", command.getName());
                    return new DuplicateKeyException("Department with name: " + command.getName() +
                            " already exists");
                })
                .map(departmentApplicationMapper::toDepartmentResponse);
    }
}
