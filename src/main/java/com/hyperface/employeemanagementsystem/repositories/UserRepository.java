package com.hyperface.employeemanagementsystem.repositories;

import com.hyperface.employeemanagementsystem.models.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserAuth,Long> {
    Optional<UserAuth> findByEmail(String email);
}
