package com.e.bambi.inventory.domain.department.entity;

import com.e.bambi.inventory.domain.shared.valueobject.DepartmentId;
import com.e.bambi.shared.kernel.domain.entity.AggregateRoot;
import lombok.Getter;

import java.util.UUID;

@Getter
public class Department extends AggregateRoot<DepartmentId> {
    private final String name;

    public Department(String name) {
        this.name = name;
    }

    public Department(DepartmentId departmentId, String name) {
        super.setId(departmentId);
        this.name = name;
    }

    public void initializeDepartment() {
        super.setId(new DepartmentId(UUID.randomUUID()));
    }
}
