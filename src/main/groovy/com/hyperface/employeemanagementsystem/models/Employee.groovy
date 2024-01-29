package com.hyperface.employeemanagementsystem.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "employee")
class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    String firstName;

    @Column
    String lastName;

    @ManyToOne
    @JoinColumn(name = "department_id")
    @JsonBackReference
    Department department;

    @ManyToMany(targetEntity = Project.class,cascade = [CascadeType.MERGE,CascadeType.PERSIST])
    @JoinTable(
            name = "employee_project",
            joinColumns = @JoinColumn(name = "employee_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "project_id",referencedColumnName = "id"))
    @JsonBackReference
    Set<Project> project=new HashSet<>();

    Employee(String firstName, String lastName, Department department,Set<Project> projectList) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.project=projectList;
        this.department = department;
    }
    Employee(){}
}
