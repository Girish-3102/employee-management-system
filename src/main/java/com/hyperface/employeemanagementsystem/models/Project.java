package com.hyperface.employeemanagementsystem.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

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

    @ManyToMany(targetEntity = Employee.class,mappedBy = "project",cascade =  {CascadeType.MERGE,CascadeType.PERSIST})
    @JsonManagedReference
    private Set<Employee> employees=new HashSet<>();

    public Project(String name, Department department) {
        this.name = name;
        this.department = department;
    }

    public Project(){}

    @PreRemove
    public void remove(){
        for(Employee e:employees){
            e.getProject().remove(this);
        }
    }
}
