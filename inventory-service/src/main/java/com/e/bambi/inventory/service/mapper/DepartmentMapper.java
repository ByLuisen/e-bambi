package com.e.bambi.inventory.service.mapper;

import com.e.bambi.inventory.service.model.Department;
import com.e.bambi.inventory.service.dto.DepartmentInputDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {
    Department departmentInputDTOToDepartment(DepartmentInputDTO departmentInputDTO);
}
