package com.example.employeemanagementsystem.services.impl;

import com.example.employeemanagementsystem.models.Department;
import com.example.employeemanagementsystem.models.Employee;
import com.example.employeemanagementsystem.models.Project;
import com.example.employeemanagementsystem.models.dtos.EmployeeDto;
import com.example.employeemanagementsystem.models.mappers.EmployeeMapper;
import com.example.employeemanagementsystem.repositories.EmployeeRepository;
import com.example.employeemanagementsystem.repositories.ProjectRepository;
import com.example.employeemanagementsystem.services.DepartmentService;
import com.example.employeemanagementsystem.services.EmployeeService;
import com.example.employeemanagementsystem.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired private EmployeeRepository employeeRepository;
    @Autowired private DepartmentService departmentService;
    @Autowired private EmployeeMapper employeeMapper;
    @Override
    public Employee createEmployee(EmployeeDto employeeDto) {
        Department department=departmentService.getDepartmentById(employeeDto.getDepartmentId());
        Employee employee=new Employee();
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setDepartment(department);
        return employeeRepository.save(employee);
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee updateEmployee(EmployeeDto employeeDto){
        Employee employee=employeeRepository.findById(employeeDto.getId()).orElseThrow();
        employeeMapper.updateEmployeeFromDto(employeeDto,employee);
        if(employeeDto.getDepartmentId()!=null) {
            Department department = departmentService.getDepartmentById(employeeDto.getDepartmentId());
            employee.setDepartment(department);
            employee.setProject(new ArrayList<>());
        }
        return employeeRepository.save(employee);
    }

    @Override
    public String deleteEmployee(Long id) {
        try {
            employeeRepository.deleteById(id);
            return "Success";
        }catch (Exception e){
            return e.getMessage();
        }
    }

}
