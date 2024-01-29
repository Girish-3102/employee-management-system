package com.hyperface.employeemanagementsystem.services;

import com.hyperface.employeemanagementsystem.models.Project;
import com.hyperface.employeemanagementsystem.models.dtos.ProjectRequest;

import java.util.List;

interface ProjectService {
    List<Project> getAllProjects();
    Project getProjectById(Long id);
    Project createProject(ProjectRequest projectRequest);
    String deleteProjectById(Long id);
    Project updateProject(Long id,String name);
    String addEmployees(Long id,List<Long> employeeIds);

    String removeEmployee(Long id,Long employeeId);
}
