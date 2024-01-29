package com.hyperface.employeemanagementsystem.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


class ProjectRequest {
    @NotEmpty(message = "Project name cannot be empty")
    String name;
    @NotNull(message = "Department ID cannot be null")
    Long departmentId;
    List<Long> employeeIds;
}
