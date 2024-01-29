package com.hyperface.employeemanagementsystem.models.dtos

import groovy.transform.builder.Builder;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

@Builder
class AuthenticationRequest {
    @Email(message = "Please enter valid email id")
    String username;
    @NotEmpty(message = "Please enter valid password")
    String password;
}
