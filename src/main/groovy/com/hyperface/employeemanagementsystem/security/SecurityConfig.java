package com.hyperface.employeemanagementsystem.security;


import com.hyperface.employeemanagementsystem.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class SecurityConfig {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final HandlerExceptionResolver exceptionResolver;

    SecurityConfig(AuthenticationProvider authenticationProvider, JwtAuthenticationFilter jwtAuthenticationFilter, @Qualifier("handlerExceptionResolver") HandlerExceptionResolver exceptionResolver) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.exceptionResolver = exceptionResolver;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req->
                        req.requestMatchers("/auth/authenticate")
                                .permitAll()
                                .requestMatchers(HttpMethod.GET,"/**").hasAnyAuthority(Role.USER.name(),Role.ADMIN.name(),Role.MANAGER.name())
                                .requestMatchers(HttpMethod.PUT,"/employee/updateDepartment/**").hasAnyAuthority(Role.ADMIN.name())
                                .requestMatchers(HttpMethod.PUT,"/employee/**").hasAnyAuthority(Role.USER.name(),Role.ADMIN.name(),Role.MANAGER.name())
                                .requestMatchers("/project/**").hasAnyAuthority(Role.ADMIN.name(),Role.MANAGER.name())
                                .requestMatchers("/**").hasAnyAuthority(Role.ADMIN.name())
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(customizer->customizer.accessDeniedHandler((request, response, accessDeniedException) -> {
                    exceptionResolver.resolveException(request,response,null,accessDeniedException);
                }));

        return http.build();
    }
}
