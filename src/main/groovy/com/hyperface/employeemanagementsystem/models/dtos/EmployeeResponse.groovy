package com.hyperface.employeemanagementsystem.models.dtos

import com.hyperface.employeemanagementsystem.models.Department
import com.hyperface.employeemanagementsystem.models.Employee
import com.hyperface.employeemanagementsystem.models.Project

class EmployeeResponse {
    Long id
    String firstName
    String lastName
    Department department
    Set<Project> projects

    EmployeeResponse(Employee employee) {
        this.id = employee.getId()
        this.firstName = (String) employee.getFirstName()
        this.lastName = (String) employee.getLastName()
        this.department = employee.getDepartment()
        this.projects = employee.getProject()
    }
}
