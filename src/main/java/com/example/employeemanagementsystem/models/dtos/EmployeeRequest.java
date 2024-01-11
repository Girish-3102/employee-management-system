package com.example.employeemanagementsystem.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class EmployeeRequest {
    @NotEmpty(message = "Employee's first name cannot be empty")
    private String firstName;
    @NotEmpty(message = "Employee's last name cannot be empty")
    private String lastName;
    @NotNull(message = "Department ID cannot be null")
    private Long departmentId;
}
