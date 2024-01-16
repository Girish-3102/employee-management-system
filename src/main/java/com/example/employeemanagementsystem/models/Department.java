package com.example.employeemanagementsystem.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "department_name")
    private String name;

    @OneToMany(mappedBy = "department", cascade = CascadeType.PERSIST)
    @JsonManagedReference
    private Set<Employee> employees;


    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<Project> projects;

    @PreRemove
    private void remove(){
        for(Employee e:employees){
            e.setDepartment(null);
        }
    }

    public Department(String name) {
        this.name = name;
    }
    public Department(){}
}
