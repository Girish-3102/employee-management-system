package com.example.employeemanagementsystem.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "department_id")
    @JsonBackReference
    private Department department;

    @ManyToMany(targetEntity = Project.class)
    @JoinTable(
            name = "employee_project",
            joinColumns = @JoinColumn(name = "employee_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "project_id",referencedColumnName = "id"))
    @JsonBackReference
    private List<Project> project=new ArrayList<>();

    public Employee(String firstName, String lastName, Department department,List<Project> projectList) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.project=projectList;
        this.department = department;
    }
    public Employee(){}
}
