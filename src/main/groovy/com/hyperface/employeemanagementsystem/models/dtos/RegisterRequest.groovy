package com.hyperface.employeemanagementsystem.models.dtos

import com.hyperface.employeemanagementsystem.models.Role
import groovy.transform.TupleConstructor
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@TupleConstructor
class RegisterRequest {
    @Email(message = "Please enter valid email id")
    String username;
    @NotEmpty(message = "Please enter valid password")
    String password;
    @NotNull(message = "Please enter valid role")
    Role role;
    @NotEmpty(message = "Employee's first name cannot be empty")
    String firstName;
    @NotEmpty(message = "Employee's last name cannot be empty")
    String lastName;
    @NotNull(message = "Department ID cannot be null")
    Long departmentId;
}
