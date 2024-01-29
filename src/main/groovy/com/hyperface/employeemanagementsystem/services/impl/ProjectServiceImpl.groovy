package com.hyperface.employeemanagementsystem.services.impl;

import com.hyperface.employeemanagementsystem.models.Department;
import com.hyperface.employeemanagementsystem.models.Project;
import com.hyperface.employeemanagementsystem.models.dtos.ProjectRequest;
import com.hyperface.employeemanagementsystem.repositories.ProjectRepository;
import com.hyperface.employeemanagementsystem.services.DepartmentService;
import com.hyperface.employeemanagementsystem.services.EmployeeService;
import com.hyperface.employeemanagementsystem.services.ProjectService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final DepartmentService departmentService;
    private final EmployeeService employeeService;

    ProjectServiceImpl(ProjectRepository projectRepository, DepartmentService departmentService, EmployeeService employeeService) {
        this.projectRepository = projectRepository;
        this.departmentService = departmentService;
        this.employeeService = employeeService;
    }

    @Override
    List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    Project getProjectById(Long id) {
        Optional<Project> project=projectRepository.findById(id);
        if(project.isPresent()){
            return project.get();
        }
        else {
            throw new EntityNotFoundException("Project with the requested ID not found");
        }
    }

    @Override
    Project createProject(ProjectRequest projectRequest) {
        Department department=departmentService.getDepartmentById(projectRequest.getDepartmentId());
        return projectRepository.save(new Project(projectRequest.getName(),department));
    }

    @Override
    String deleteProjectById(Long id) {
            projectRepository.deleteById(id);
            return "Success";
    }
    @Override
    Project updateProject(Long id,String name){
        Project project=getProjectById(id);
        project.setName(name);
        return projectRepository.save(project);
    }
    @Override
    String addEmployees(Long id, List<Long> employeeIds) {
        Project project=getProjectById(id);
        for (Long employeeId:employeeIds){
            employeeService.assignProject(employeeId,project);
        }
        return "Success";
    }

    @Override
    String removeEmployee(Long id, Long employeeId) {
        Project project=getProjectById(id);
        employeeService.unAssignProject(employeeId,project);
        return "Successfully removed";
    }
}
