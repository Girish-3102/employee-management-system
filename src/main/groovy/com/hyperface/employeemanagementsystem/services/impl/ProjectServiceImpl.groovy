package com.hyperface.employeemanagementsystem.services.impl;

import com.hyperface.employeemanagementsystem.models.Department;
import com.hyperface.employeemanagementsystem.models.Project
import com.hyperface.employeemanagementsystem.models.Role
import com.hyperface.employeemanagementsystem.models.UserAuth;
import com.hyperface.employeemanagementsystem.models.dtos.ProjectRequest;
import com.hyperface.employeemanagementsystem.repositories.ProjectRepository
import com.hyperface.employeemanagementsystem.repositories.UserRepository
import com.hyperface.employeemanagementsystem.security.SecurityUtils;
import com.hyperface.employeemanagementsystem.services.DepartmentService;
import com.hyperface.employeemanagementsystem.services.EmployeeService;
import com.hyperface.employeemanagementsystem.services.ProjectService
import groovy.transform.TupleConstructor;
import jakarta.persistence.EntityNotFoundException
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final DepartmentService departmentService;
    private final EmployeeService employeeService;
    private final SecurityUtils securityUtils

    ProjectServiceImpl(ProjectRepository projectRepository, DepartmentService departmentService, EmployeeService employeeService, SecurityUtils securityUtils) {
        this.projectRepository = projectRepository
        this.departmentService = departmentService
        this.employeeService = employeeService
        this.securityUtils = securityUtils
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
        verifyManagerHasAccess(department.getId())
        return projectRepository.save(new Project(projectRequest.getName(),department));
    }

    @Override
    String deleteProjectById(Long id) {
            Project project=getProjectById(id)
            verifyManagerHasAccess(project.getDepartment().getId())
            projectRepository.deleteById(id);
            return "Success";
    }
    @Override
    Project updateProject(Long id,String name){
        Project project=getProjectById(id);
        verifyManagerHasAccess(project.getDepartment().getId())
        project.setName(name);
        return projectRepository.save(project);
    }
    @Override
    String addEmployees(Long id, List<Long> employeeIds) {
        Project project=getProjectById(id);
        verifyManagerHasAccess(project.getDepartment().getId())
        for (Long employeeId:employeeIds){
            employeeService.assignProject(employeeId,project);
        }
        return "Success";
    }

    @Override
    String removeEmployee(Long id, Long employeeId) {
        Project project=getProjectById(id);
        verifyManagerHasAccess(project.getDepartment().getId())
        employeeService.unAssignProject(employeeId,project);
        return "Successfully removed";
    }

    void verifyManagerHasAccess(Long departmentId){
        def isManager=securityUtils.hasRole(Role.MANAGER.name())
        if(isManager){
            UserAuth userAuth=securityUtils.getPrincipal()
            if(userAuth.getEmployee().getDepartment().getId()!=departmentId){
                throw new AccessDeniedException("Manager department different from project department")
            }
        }
    }
}
