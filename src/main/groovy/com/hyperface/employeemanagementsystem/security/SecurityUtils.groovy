package com.hyperface.employeemanagementsystem.security

import com.hyperface.employeemanagementsystem.models.UserAuth
import com.hyperface.employeemanagementsystem.repositories.UserRepository
import com.hyperface.employeemanagementsystem.services.impl.UserService
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
class SecurityUtils {
    private final UserService userService

    SecurityUtils(UserService userService) {
        this.userService = userService
    }

    UserAuth getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            def userDetails = authentication.getPrincipal()
            return userService.findUserByEmail(userDetails.getUsername())
        }
        return null;
    }

    boolean hasRole(String role) {
        UserAuth userDetails = getPrincipal();
        return userDetails != null && userDetails.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority() == role);
    }

    boolean isLoggedInId(Long id){
        print(id)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            def userDetails = authentication.getPrincipal()
            return userService.findUserByEmail(userDetails.getUsername()).getEmployee().getId()==id
        }
        return false;
    }
}
