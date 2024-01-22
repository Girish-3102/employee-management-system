package com.hyperface.employeemanagementsystem.config;


import com.hyperface.employeemanagementsystem.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfiguration {
    private final UserRepository userRepository;

    @Bean
    private UserDetailsService getUserDetailsService(){
        return username -> userRepository.findByEmail(username).orElseThrow(EntityNotFoundException::new);
    }
}