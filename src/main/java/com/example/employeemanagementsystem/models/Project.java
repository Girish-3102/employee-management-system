package com.example.employeemanagementsystem.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "project_name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "department_id")
    @JsonBackReference
    private Department department;

    @ManyToMany(targetEntity = Employee.class,mappedBy = "project")
    @JsonManagedReference
    private List<Employee> employees=new ArrayList<>();

    public Project(String name, Department department) {
        this.name = name;
        this.department = department;
    }
    @PreRemove
    public void remove(){
        for(Employee e:employees){
            e.getProject().remove(this);
        }
    }

    public Project(){}
}
