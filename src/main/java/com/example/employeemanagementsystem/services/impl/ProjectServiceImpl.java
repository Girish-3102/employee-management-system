package com.example.employeemanagementsystem.services.impl;

import com.example.employeemanagementsystem.models.Department;
import com.example.employeemanagementsystem.models.Project;
import com.example.employeemanagementsystem.models.dtos.ProjectRequest;
import com.example.employeemanagementsystem.repositories.DepartmentRepository;
import com.example.employeemanagementsystem.repositories.ProjectRepository;
import com.example.employeemanagementsystem.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired ProjectRepository projectRepository;
    @Autowired DepartmentRepository departmentRepository;
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
        Department department=departmentRepository.findById(projectRequest.getDepartmentId()).orElse(null);
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
}
