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
    private Long id;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;

    @NotNull
    private Long departmentId;

    private Long projectId;

}
