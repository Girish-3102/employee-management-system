package com.hyperface.employeemanagementsystem.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@Table(name = "department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "department_name")
    private String name;

    @OneToMany(mappedBy = "department", cascade = CascadeType.PERSIST)
    @JsonManagedReference
    private Set<Employee> employees=new HashSet<>();


    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<Project> projects=new HashSet<>();

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
