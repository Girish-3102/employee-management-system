package com.example.employeemanagementsystem.services.impl;

import com.example.employeemanagementsystem.models.Department;
import com.example.employeemanagementsystem.models.Employee;
import com.example.employeemanagementsystem.models.Project;
import com.example.employeemanagementsystem.models.dtos.ProjectRequest;
import com.example.employeemanagementsystem.repositories.DepartmentRepository;
import com.example.employeemanagementsystem.repositories.EmployeeRepository;
import com.example.employeemanagementsystem.repositories.ProjectRepository;
import com.example.employeemanagementsystem.services.DepartmentService;
import com.example.employeemanagementsystem.services.EmployeeService;
import com.example.employeemanagementsystem.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired ProjectRepository projectRepository;
    @Autowired DepartmentService departmentService;
    @Autowired EmployeeService employeeService;
    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Project getProjectById(Long id) {
        return projectRepository.findById(id).orElse(null);
    }

    @Override
    public Project createProject(ProjectRequest projectRequest) {
        Department department=departmentService.getDepartmentById(projectRequest.getDepartmentId());
        return projectRepository.save(new Project(projectRequest.getName(),department));
    }

    @Override
    public String deleteProjectById(Long id) {
        try {
            projectRepository.deleteById(id);
            return "Success";
        }catch (Exception e){
            return e.getMessage();
        }
    }
    @Override
    public Project updateProject(Long id,String name){
        Optional<Project> project=projectRepository.findById(id);
        if(project.isPresent()){
            project.get().setName(name);
            return projectRepository.save(project.get());
        }
        return null;
    }

    @Override
    public String addEmployees(Long id, List<Long> employeeIds) {
        Project project=projectRepository.findById(id).orElseThrow();
        List<Employee> employeeList=project.getEmployees();
        for (Long employeeId:employeeIds){
            Employee employee=employeeService.getEmployeeById(employeeId);
            System.out.println(employee.getId());
            employeeList.add(employee);
        }
        project.setEmployees(employeeList);
        projectRepository.save(project);
        return "Success";
    }
}
