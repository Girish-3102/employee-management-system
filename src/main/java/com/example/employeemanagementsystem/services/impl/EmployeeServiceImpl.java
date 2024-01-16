package com.example.employeemanagementsystem.services.impl;

import com.example.employeemanagementsystem.models.Department;
import com.example.employeemanagementsystem.models.Employee;
import com.example.employeemanagementsystem.models.Project;
import com.example.employeemanagementsystem.models.dtos.EmployeeRequest;
import com.example.employeemanagementsystem.models.mappers.EmployeeMapper;
import com.example.employeemanagementsystem.repositories.EmployeeRepository;
import com.example.employeemanagementsystem.services.DepartmentService;
import com.example.employeemanagementsystem.services.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    final private EmployeeRepository employeeRepository;
    final private DepartmentService departmentService;
    final private EmployeeMapper employeeMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, DepartmentService departmentService, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.departmentService = departmentService;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public Employee createEmployee(EmployeeRequest employeeRequest) {
        Department department=departmentService.getDepartmentById(employeeRequest.getDepartmentId());
        Employee employee=new Employee();
        employeeMapper.updateEmployeeFromDto(employeeRequest,employee);
        employee.setDepartment(department);
        return employeeRepository.save(employee);
    }

    @Override
    public Employee getEmployeeById(Long id) {
        Optional<Employee> employee=employeeRepository.findById(id);
        if(employee.isPresent()){
            return employee.get();
        }
        else {
            throw new EntityNotFoundException("Employee with the requested ID not found");
        }
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee updateEmployee(Long id,EmployeeRequest employeeRequest){
        Employee employee=getEmployeeById(id);
        employeeMapper.updateEmployeeFromDto(employeeRequest,employee);
        if(employeeRequest.getDepartmentId()!=null) {
            Department department = departmentService.getDepartmentById(employeeRequest.getDepartmentId());
            employee.setDepartment(department);
            employee.getProject().clear();
        }
        return employeeRepository.save(employee);
    }
    @Override
    public String deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
        return "Success";
    }

    @Override
    public Employee assignProject(Long id, Project project) {
        Employee employee=getEmployeeById(id);
        employee.getProject().add(project);
        return employeeRepository.save(employee);
    }

    @Override
    public Employee unAssignProject(Long id, Project project) {
        Employee employee=getEmployeeById(id);
        employee.getProject().remove(project);
        return employeeRepository.save(employee);
    }
}
