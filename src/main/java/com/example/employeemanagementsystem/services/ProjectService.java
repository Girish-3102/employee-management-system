package com.example.employeemanagementsystem.services;

import com.example.employeemanagementsystem.models.Project;
import com.example.employeemanagementsystem.models.dtos.ProjectRequest;

import java.util.List;

public interface ProjectService {
    List<Project> getAllProjects();
    Project getProjectById(Long id);
    Project createProject(ProjectRequest projectRequest);

    String deleteProjectById(Long id);

}
