package com.hyperface.employeemanagementsystem.services.impl;


import com.hyperface.employeemanagementsystem.models.Employee;
import com.hyperface.employeemanagementsystem.models.Role;
import com.hyperface.employeemanagementsystem.models.UserAuth;
import com.hyperface.employeemanagementsystem.models.dtos.AuthenticationRequest;
import com.hyperface.employeemanagementsystem.models.dtos.AuthenticationResponse;
import com.hyperface.employeemanagementsystem.models.dtos.EmployeeRequest;
import com.hyperface.employeemanagementsystem.models.dtos.RegisterRequest;
import com.hyperface.employeemanagementsystem.repositories.UserRepository;
import com.hyperface.employeemanagementsystem.services.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional;

@Service
class AuthenticationService {
    UserRepository userRepository;
    JwtService jwtService;
    AuthenticationManager authenticationManager;
    EmployeeService employeeService;
    PasswordEncoder passwordEncoder;

    AuthenticationService(UserRepository userRepository, JwtService jwtService, AuthenticationManager authenticationManager, EmployeeService employeeService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository
        this.jwtService = jwtService
        this.authenticationManager = authenticationManager
        this.employeeService = employeeService
        this.passwordEncoder = passwordEncoder
    }

    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) throws EntityNotFoundException{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()
                    )
            );
            UserAuth userAuth = userRepository.findByEmail(authenticationRequest.getUsername()).orElseThrow(() -> new EntityNotFoundException("User not found"));
            String jwtToken = jwtService.generateToken(userAuth);
            return AuthenticationResponse.builder().token(jwtToken).build()
    }

    @Transactional
    AuthenticationResponse register(RegisterRequest registerRequest) {
        try{
            EmployeeRequest employeeRequest=new EmployeeRequest(registerRequest.getFirstName(), registerRequest.getLastName(), registerRequest.getDepartmentId());
            Employee employee=employeeService.createEmployee(employeeRequest);
            UserAuth user=new UserAuth();
            user.setEmail(registerRequest.getUsername())
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()))
            user.setEmployee(employee)
            user.setRole(registerRequest.getRole())
            userRepository.save(user);
            String jwtToken=jwtService.generateToken(user);
            return AuthenticationResponse.builder().token(jwtToken).build();
        }catch (Exception e){
            throw new RuntimeException(e.getMessage())
        }
    }
}
