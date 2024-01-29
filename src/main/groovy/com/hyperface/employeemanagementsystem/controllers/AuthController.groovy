package com.hyperface.employeemanagementsystem.controllers;


import com.hyperface.employeemanagementsystem.models.dtos.AuthenticationRequest;
import com.hyperface.employeemanagementsystem.models.dtos.AuthenticationResponse;
import com.hyperface.employeemanagementsystem.models.dtos.RegisterRequest;
import com.hyperface.employeemanagementsystem.services.impl.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
class AuthController {
    private final AuthenticationService authenticationService;
    AuthController(AuthenticationService authenticationService){
        this.authenticationService=authenticationService
    }

    @PostMapping("/authenticate")
    ResponseEntity<AuthenticationResponse> authenticate(
            @Valid @RequestBody AuthenticationRequest authenticationRequest
    ){
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest))
    }

    @PostMapping("/register")
    ResponseEntity<AuthenticationResponse> authenticate(
           @Valid @RequestBody RegisterRequest registerRequest
    ){
        return ResponseEntity.ok(authenticationService.register(registerRequest))
    }
}
