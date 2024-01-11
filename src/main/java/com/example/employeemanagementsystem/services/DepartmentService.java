package com.example.employeemanagementsystem.services;

import com.example.employeemanagementsystem.models.Department;
import com.example.employeemanagementsystem.models.Employee;
import com.example.employeemanagementsystem.models.Project;
import com.example.employeemanagementsystem.models.dtos.DepartmentRequest;

import java.util.List;
import java.util.Set;

public interface DepartmentService {
    List<Department> getAllDepartments();
    Set<Project> getAllProjectsByDepartmentId(Long id);
    Set<Employee> getAllEmployeesByDepartmentId(Long id);
    Department createDepartment(DepartmentRequest departmentRequest);
    Department getDepartmentById(Long id);
    String deleteDepartmentById(Long id);
    Department updateDepartmentName(Long id,String name);
}
