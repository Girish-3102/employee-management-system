package com.hyperface.employeemanagementsystem.models.dtos

import groovy.transform.TupleConstructor;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@TupleConstructor
class ProjectRequest {
    @NotEmpty(message = "Project name cannot be empty")
    String name;
    @NotNull(message = "Department ID cannot be null")
    Long departmentId;
    List<Long> employeeIds;
}
