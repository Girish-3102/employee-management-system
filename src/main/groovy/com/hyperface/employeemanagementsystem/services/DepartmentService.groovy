package com.hyperface.employeemanagementsystem.services;

import com.hyperface.employeemanagementsystem.models.Department;
import com.hyperface.employeemanagementsystem.models.dtos.DepartmentRequest;

import java.util.List;

interface DepartmentService {
    List<Department> getAllDepartments();
    Department createDepartment(DepartmentRequest departmentRequest);
    Department getDepartmentById(Long id);
    String deleteDepartmentById(Long id);
    Department updateDepartmentName(Long id,String name);
}
