package com.hyperface.employeemanagementsystem.services.impl;


import com.hyperface.employeemanagementsystem.models.Employee;
import com.hyperface.employeemanagementsystem.models.Role;
import com.hyperface.employeemanagementsystem.models.UserAuth;
import com.hyperface.employeemanagementsystem.models.dtos.AuthenticationRequest;
import com.hyperface.employeemanagementsystem.models.dtos.AuthenticationResponse;
import com.hyperface.employeemanagementsystem.models.dtos.EmployeeRequest;
import com.hyperface.employeemanagementsystem.models.dtos.RegisterRequest;
import com.hyperface.employeemanagementsystem.models.mappers.EmployeeMapper;
import com.hyperface.employeemanagementsystem.repositories.UserRepository;
import com.hyperface.employeemanagementsystem.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmployeeService employeeService;

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );
        UserAuth userAuth=userRepository.findByEmail(authenticationRequest.getUsername()).orElseThrow();
        String jwtToken=jwtService.generateToken(userAuth);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        EmployeeRequest employeeRequest=new EmployeeRequest(registerRequest.getFirstName(), registerRequest.getLastName(), registerRequest.getDepartmentId());
        Employee employee=employeeService.createEmployee(employeeRequest);
        UserAuth user=UserAuth.builder()
                .role(Role.valueOf(registerRequest.getRole()))
                .email(registerRequest.getUsername())
                .password(registerRequest.getPassword())
                .employee(employee)
                .build();
        userRepository.save(user);
        String jwtToken=jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}