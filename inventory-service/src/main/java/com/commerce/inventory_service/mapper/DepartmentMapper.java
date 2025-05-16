package com.commerce.inventory_service.mapper;

import com.commerce.inventory_service.domain.Department;
import com.commerce.inventory_service.dto.DepartmentInputDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {
    Department departmentInputDTOToDepartment(DepartmentInputDTO departmentInputDTO);
}
