package com.example.employeemanagementsystem.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class EmployeeDto {
    private Long id;
    private String firstName;
    private String lastName;
    private Long departmentId;
}
