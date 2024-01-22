package com.hyperface.employeemanagementsystem.models.dtos;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    @Email(message = "Please enter valid email id")
    private String username;
    @NotEmpty(message = "Please enter valid password")
    private String password;
}
