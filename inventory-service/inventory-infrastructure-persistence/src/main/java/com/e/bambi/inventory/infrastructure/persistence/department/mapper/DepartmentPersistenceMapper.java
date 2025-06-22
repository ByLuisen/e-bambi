package com.e.bambi.inventory.infrastructure.persistence.department.mapper;

import com.e.bambi.inventory.domain.department.entity.Department;
import com.e.bambi.inventory.domain.shared.valueobject.DepartmentId;
import com.e.bambi.inventory.infrastructure.persistence.department.entity.DepartmentEntity;
import org.springframework.stereotype.Component;

@Component
public class DepartmentPersistenceMapper {

    public Department toDepartment(DepartmentEntity entity) {
        return new Department(
                new DepartmentId(entity.getId()),
                entity.getName()
        );
    }

    public DepartmentEntity toDepartmentEntity(Department department) {
        return new DepartmentEntity(
                department.getId().getValue(),
                department.getName()
        );
    }

}
