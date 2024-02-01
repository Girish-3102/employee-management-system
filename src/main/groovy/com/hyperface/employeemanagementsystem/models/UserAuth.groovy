package com.hyperface.employeemanagementsystem.models

import groovy.transform.TupleConstructor
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Entity
@TupleConstructor
class UserAuth implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true)
    String email;

    @Column
    String password;

    @Column
    @Enumerated(EnumType.STRING)
    Role role;

    @OneToOne(cascade = [CascadeType.ALL])
    Employee employee;

    @Override
    Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }


    @Override
    String getPassword() {
        return password;
    }

    @Override
    String getUsername() {
        return email;
    }

    @Override
    boolean isAccountNonExpired() {
        return true;
    }

    @Override
    boolean isAccountNonLocked() {
        return true;
    }

    @Override
    boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    boolean isEnabled() {
        return true;
    }
}
