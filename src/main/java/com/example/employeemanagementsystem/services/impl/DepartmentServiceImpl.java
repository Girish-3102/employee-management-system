package com.example.employeemanagementsystem.services.impl;

import com.example.employeemanagementsystem.models.Department;
import com.example.employeemanagementsystem.models.Employee;
import com.example.employeemanagementsystem.models.Project;
import com.example.employeemanagementsystem.models.dtos.DepartmentRequest;
import com.example.employeemanagementsystem.repositories.DepartmentRepository;
import com.example.employeemanagementsystem.repositories.ProjectRepository;
import com.example.employeemanagementsystem.services.DepartmentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public Set<Project> getAllProjectsByDepartmentId(Long id) {
        return getDepartmentById(id).getProjects();
    }

    @Override
    public Set<Employee> getAllEmployeesByDepartmentId(Long id) {
        return getDepartmentById(id).getEmployees();
    }

    @Override
    public Department createDepartment(DepartmentRequest departmentRequest) {
        return departmentRepository.save(new Department(departmentRequest.getName()));
    }

    @Override
    public Department getDepartmentById(Long id) {
        Optional<Department> department=departmentRepository.findById(id);
        if(department.isPresent()){
            return department.get();
        }
        else {
            throw new EntityNotFoundException("Department with the requested ID not found.");
        }
    }

    @Override
    public Department updateDepartmentName(Long id, String name) {
        Department department=getDepartmentById(id);
        department.setName(name);
        return departmentRepository.save(department);
    }

    @Override
    public String deleteDepartmentById(Long id) {
        departmentRepository.deleteById(id);
        return "Success";
    }
}
