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
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {
    final private ProjectRepository projectRepository;
    final private DepartmentService departmentService;
    final private EmployeeService employeeService;

    public ProjectServiceImpl(ProjectRepository projectRepository, DepartmentService departmentService, EmployeeService employeeService) {
        this.projectRepository = projectRepository;
        this.departmentService = departmentService;
        this.employeeService = employeeService;
    }

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Project getProjectById(Long id) {
        Optional<Project> project=projectRepository.findById(id);
        if(project.isPresent()){
            return project.get();
        }
        else {
            throw new EntityNotFoundException("Project with the requested ID not found");
        }
    }

    @Override
    public Project createProject(ProjectRequest projectRequest) {
        Department department=departmentService.getDepartmentById(projectRequest.getDepartmentId());
        return projectRepository.save(new Project(projectRequest.getName(),department));
    }

    @Override
    public String deleteProjectById(Long id) {
            projectRepository.deleteById(id);
            return "Success";
    }
    @Override
    public Project updateProject(Long id,String name){
        Project project=getProjectById(id);
        project.setName(name);
        return projectRepository.save(project);
    }

    @Override
    public String addEmployees(Long id, List<Long> employeeIds) {
        Project project=getProjectById(id);
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
