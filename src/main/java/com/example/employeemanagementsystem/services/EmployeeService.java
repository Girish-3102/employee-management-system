package com.example.employeemanagementsystem.services;

import com.example.employeemanagementsystem.models.Employee;
import com.example.employeemanagementsystem.models.dtos.EmployeeDto;

import java.util.List;

public interface EmployeeService {
    Employee createEmployee(EmployeeDto employeeDto);
    Employee getEmployeeById(Long id);
    List<Employee> getAllEmployees();
    Employee updateEmployee(EmployeeDto employeeDto);
    String deleteEmployee(Long id);
}