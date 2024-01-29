package com.hyperface.employeemanagementsystem.models.dtos
import jakarta.validation.constraints.NotEmpty;

class DepartmentRequest {
    @NotEmpty(message = "The department name cannot be empty")
    String name;
}
