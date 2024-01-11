package com.example.employeemanagementsystem.services.impl;

import com.example.employeemanagementsystem.models.Department;
import com.example.employeemanagementsystem.models.Employee;
import com.example.employeemanagementsystem.models.Project;
import com.example.employeemanagementsystem.models.dtos.DepartmentRequest;
import com.example.employeemanagementsystem.repositories.DepartmentRepository;
import com.example.employeemanagementsystem.repositories.ProjectRepository;
import com.example.employeemanagementsystem.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired private DepartmentRepository departmentRepository;

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public List<Project> getAllProjectsByDepartmentId(Long id) {
        return getDepartmentById(id).getProjects();
    }

    @Override
    public List<Employee> getAllEmployeesByDepartmentId(Long id) {
        return getDepartmentById(id).getEmployees();
    }

    @Override
    public Department createDepartment(DepartmentRequest departmentRequest) {
        return departmentRepository.save(new Department(departmentRequest.getName()));
    }

    @Override
    public Department getDepartmentById(Long id) {
        Department department=departmentRepository.findById(id).orElse(null);
        return department;
    }

    @Override
    public Department updateDepartmentName(Long id, String name) {
        Department department=departmentRepository.findById(id).orElseThrow();
        department.setName(name);
        return department;
    }

    @Override
    public String deleteDepartmentById(Long id) {
        try{
            departmentRepository.deleteById(id);
            return "Success";
        }catch (Exception e){
            return e.getMessage();
        }
    }
}
