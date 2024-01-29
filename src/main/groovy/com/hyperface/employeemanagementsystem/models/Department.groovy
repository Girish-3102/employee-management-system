package com.hyperface.employeemanagementsystem.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "department")
class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "department_name")
    String name;

    @OneToMany(mappedBy = "department", cascade = CascadeType.PERSIST)
    @JsonManagedReference
    Set<Employee> employees=new HashSet<>();


    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    @JsonManagedReference
    Set<Project> projects=new HashSet<>();

    @PreRemove
    void remove(){
        for(Employee e:employees){
            e.setDepartment(null);
        }
    }

    Department(String name) {
        this.name = name;
    }
    Department(){}
}
