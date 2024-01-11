package com.example.employeemanagementsystem.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class DepartmentRequest {
    @NotEmpty(message = "The department name cannot be empty")
    private String name;
}
