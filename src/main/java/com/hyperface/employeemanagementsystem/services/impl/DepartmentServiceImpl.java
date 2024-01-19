package com.hyperface.employeemanagementsystem.services.impl;

import com.hyperface.employeemanagementsystem.models.Department;
import com.hyperface.employeemanagementsystem.models.dtos.DepartmentRequest;
import com.hyperface.employeemanagementsystem.repositories.DepartmentRepository;
import com.hyperface.employeemanagementsystem.services.DepartmentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
