package com.hyperface.employeemanagementsystem.services.impl

import com.hyperface.employeemanagementsystem.models.UserAuth
import com.hyperface.employeemanagementsystem.repositories.UserRepository
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service


@Service
class UserService {
    private final UserRepository userRepository

    UserService(UserRepository userRepository) {
        this.userRepository = userRepository
    }

    UserAuth findUserByEmail(String emailId){
        def userAuth=userRepository.findByEmail(emailId);
        if(userAuth.isEmpty()){
            throw new UsernameNotFoundException("User with requested email not found!")
        }
        return userAuth.get()
    }
}
