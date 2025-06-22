package com.e.bambi.inventory.application.department.mapper;

import com.e.bambi.inventory.application.department.dto.command.CreateDepartmentCommand;
import com.e.bambi.inventory.application.department.dto.command.UpdateDepartmentCommand;
import com.e.bambi.inventory.application.department.dto.response.DepartmentResponse;
import com.e.bambi.inventory.domain.department.entity.Department;
import org.springframework.stereotype.Component;

@Component
public class DepartmentApplicationMapper {

    public Department createDepartmentCommandToDepartment(CreateDepartmentCommand command) {
        return new Department(command.getName());
    }

    public Department updateDepartmentCommandToDepartment(UpdateDepartmentCommand command) {
        return new Department(
                command.getDepartmentId(),
                command.getName()
        );
    }

    public DepartmentResponse toDepartmentResponse(Department department) {
        return new DepartmentResponse(
                department.getId().getValue(),
                department.getName()
        );
    }
}
