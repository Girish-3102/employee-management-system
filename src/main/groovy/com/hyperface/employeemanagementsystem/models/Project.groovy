package com.hyperface.employeemanagementsystem.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "project")
class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "project_name")
    String name;

    @ManyToOne
    @JoinColumn(name = "department_id")
    @JsonBackReference
    Department department;

    @ManyToMany(targetEntity = Employee.class,mappedBy = "project",cascade =  [CascadeType.MERGE,CascadeType.PERSIST])
    @JsonManagedReference
    Set<Employee> employees=new HashSet<>();

    Project(String name, Department department) {
        this.name = name;
        this.department = department;
    }

    Project(){}

    @PreRemove
    void remove(){
        for(Employee e:employees){
            e.getProject().remove(this);
        }
    }
}
