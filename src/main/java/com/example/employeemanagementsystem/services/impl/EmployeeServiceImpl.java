package com.example.employeemanagementsystem.services.impl;

import com.example.employeemanagementsystem.models.Department;
import com.example.employeemanagementsystem.models.Employee;
import com.example.employeemanagementsystem.models.Project;
import com.example.employeemanagementsystem.models.dtos.EmployeeRequest;
import com.example.employeemanagementsystem.repositories.EmployeeRepository;
import com.example.employeemanagementsystem.repositories.ProjectRepository;
import com.example.employeemanagementsystem.services.DepartmentService;
import com.example.employeemanagementsystem.services.EmployeeService;
import com.example.employeemanagementsystem.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired private EmployeeRepository employeeRepository;
    @Autowired private ProjectRepository projectRepository;
    @Autowired private DepartmentService departmentService;

    @Autowired private ProjectService projectService;
    @Override
    public Employee createEmployee(EmployeeRequest employeeRequest) {
        Department department=departmentService.getDepartmentById(employeeRequest.getDepartmentId());
        Employee employee=new Employee();
        employee.setFirstName(employeeRequest.getFirstName());
        employee.setLastName(employeeRequest.getLastName());
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
    public Employee updateEmployee(EmployeeRequest employeeRequest){
        Employee employee=employeeRepository.findById(employeeRequest.getId()).orElse(null);
        if(employeeRequest.getDepartmentId()!=null) {
            Department department = departmentService.getDepartmentById(employeeRequest.getDepartmentId());
            assert employee != null;
            employee.setDepartment(department);
        }
        if(employeeRequest.getProjectId()!=null){
            Project project =projectService.getProjectById(employeeRequest.getProjectId());
            List<Project> projectList=employee.getProject();
            projectList.add(project);
            employee.setProject(projectList);
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
