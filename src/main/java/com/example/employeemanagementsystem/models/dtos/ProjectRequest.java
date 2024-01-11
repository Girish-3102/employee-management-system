package com.example.employeemanagementsystem.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
public class ProjectRequest {
    @NotEmpty(message = "Project name cannot be empty")
    private String name;
    @NotNull(message = "Department ID cannot be null")
    private Long departmentId;
    private List<Long> employeeIds;
}
