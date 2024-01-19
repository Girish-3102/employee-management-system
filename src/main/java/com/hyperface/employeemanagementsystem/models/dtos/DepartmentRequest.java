package com.hyperface.employeemanagementsystem.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentRequest {
    @NotEmpty(message = "The department name cannot be empty")
    private String name;
}
