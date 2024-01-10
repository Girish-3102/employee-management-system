package com.example.employeemanagementsystem.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class DepartmentRequest {
    @NotEmpty
    private String name;
}
