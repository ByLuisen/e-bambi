package com.e.bambi.inventory.application.department.handler.command;

import com.e.bambi.inventory.application.department.dto.command.UpdateDepartmentCommand;
import com.e.bambi.inventory.application.department.dto.response.DepartmentResponse;
import com.e.bambi.inventory.application.department.mapper.DepartmentApplicationMapper;
import com.e.bambi.inventory.application.department.port.outbound.repository.DepartmentRepository;
import com.e.bambi.inventory.domain.exception.DepartmentNotFoundException;
import com.e.bambi.shared.kernel.application.bus.CommandHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateDepartmentCommandHandler implements CommandHandler<Mono<DepartmentResponse>, UpdateDepartmentCommand> {

    private final DepartmentRepository departmentRepository;
    private final DepartmentApplicationMapper departmentApplicationMapper;

    @Override
    public Mono<DepartmentResponse> handle(UpdateDepartmentCommand command) {
        UUID departmentId = command.getDepartmentId().getValue();
        return departmentRepository
                .update(departmentApplicationMapper.updateDepartmentCommandToDepartment(command))
                .switchIfEmpty(Mono.error(new DepartmentNotFoundException("Department with id: " + departmentId +
                        " could not be found"))
                ).onErrorMap(DuplicateKeyException.class, e -> {
                            log.error("Department with name: {} already exists", command.getName());
                            return new DuplicateKeyException("Department with name: " + command.getName() +
                                    " already exists");
                        }
                ).map(departmentApplicationMapper::toDepartmentResponse);
    }
}
