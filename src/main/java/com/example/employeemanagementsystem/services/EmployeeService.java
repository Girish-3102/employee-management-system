package com.example.employeemanagementsystem.services;

import com.example.employeemanagementsystem.models.Employee;
import com.example.employeemanagementsystem.models.dtos.EmployeeRequest;

import java.util.List;

public interface EmployeeService {
    Employee createEmployee(EmployeeRequest employeeRequest);
    Employee getEmployeeById(Long id);
    List<Employee> getAllEmployees();
    Employee updateEmployee(Long id,EmployeeRequest employeeRequest);
    String deleteEmployee(Long id);
}