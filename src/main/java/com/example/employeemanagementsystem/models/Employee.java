package com.example.employeemanagementsystem.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "department_id")
    @JsonBackReference
    private Department department;

    @ManyToMany(targetEntity = Project.class,cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinTable(
            name = "employee_project",
            joinColumns = @JoinColumn(name = "employee_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "project_id",referencedColumnName = "id"))
    @JsonBackReference
    private Set<Project> project=new HashSet<>();

    public Employee(String firstName, String lastName, Department department,Set<Project> projectList) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.project=projectList;
        this.department = department;
    }
    public Employee(){}
}
