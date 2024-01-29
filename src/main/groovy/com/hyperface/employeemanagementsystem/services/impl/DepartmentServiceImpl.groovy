package com.hyperface.employeemanagementsystem.services.impl;

import com.hyperface.employeemanagementsystem.models.Department;
import com.hyperface.employeemanagementsystem.models.dtos.DepartmentRequest;
import com.hyperface.employeemanagementsystem.repositories.DepartmentRepository;
import com.hyperface.employeemanagementsystem.services.DepartmentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
class DepartmentServiceImpl implements DepartmentService {
    DepartmentRepository departmentRepository;
    DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    Department createDepartment(DepartmentRequest departmentRequest) {
        return departmentRepository.save(new Department(departmentRequest.getName()));
    }

    @Override
    Department getDepartmentById(Long id) {
        Optional<Department> department=departmentRepository.findById(id);
        if(department.isPresent()){
            return department.get();
        }
        else {
            throw new EntityNotFoundException("Department with the requested ID not found.");
        }
    }

    @Override
    Department updateDepartmentName(Long id, String name) {
        Department department=getDepartmentById(id);
        department.setName(name);
        return departmentRepository.save(department);
    }

    @Override
    String deleteDepartmentById(Long id) {
        departmentRepository.deleteById(id);
        return "Success";
    }
}
