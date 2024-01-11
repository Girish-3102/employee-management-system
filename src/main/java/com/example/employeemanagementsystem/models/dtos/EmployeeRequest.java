package com.example.employeemanagementsystem.models.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class EmployeeRequest {
    private String firstName;
    private String lastName;
    private Long departmentId;
}
