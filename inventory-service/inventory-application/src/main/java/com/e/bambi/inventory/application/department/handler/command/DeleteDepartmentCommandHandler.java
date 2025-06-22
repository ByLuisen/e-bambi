package com.e.bambi.inventory.application.department.handler.command;

import com.e.bambi.inventory.application.department.dto.command.DeleteDepartmentCommand;
import com.e.bambi.inventory.application.department.port.outbound.repository.DepartmentRepository;
import com.e.bambi.inventory.domain.exception.DepartmentNotFoundException;
import com.e.bambi.shared.kernel.application.bus.CommandHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeleteDepartmentCommandHandler implements CommandHandler<Mono<Void>, DeleteDepartmentCommand> {

    private final DepartmentRepository departmentRepository;

    @Override
    public Mono<Void> handle(DeleteDepartmentCommand command) {
        return departmentRepository
                .deleteById(command.getDepartmentId())
                .handle((updatedRows, sink) -> {
                    if (updatedRows < 1) {
                        log.error("Department with id: {} could not be found", command.getDepartmentId().getValue());
                        sink.error(new DepartmentNotFoundException("Department with id: " +
                                command.getDepartmentId().getValue() + " could not be found"));
                    } else {
                        sink.complete();
                    }
                });
    }
}
