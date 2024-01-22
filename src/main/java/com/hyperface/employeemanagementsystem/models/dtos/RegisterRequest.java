package com.hyperface.employeemanagementsystem.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String username;
    private String password;
    private String role;
    @NotEmpty(message = "Employee's first name cannot be empty")
    private String firstName;
    @NotEmpty(message = "Employee's last name cannot be empty")
    private String lastName;
    @NotNull(message = "Department ID cannot be null")
    private Long departmentId;
}
