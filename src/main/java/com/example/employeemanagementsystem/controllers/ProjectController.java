package com.example.employeemanagementsystem.controllers;


import com.example.employeemanagementsystem.models.Project;
import com.example.employeemanagementsystem.models.dtos.ProjectRequest;
import com.example.employeemanagementsystem.services.ProjectService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/project")
public class ProjectController {
    ProjectService projectService;
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }
    @GetMapping("")
    public List<Project> getAllProjects(){
        return projectService.getAllProjects();
    }
    @GetMapping("/{id}")
    public Project getProjectById(@PathVariable("id") Long id){
        return projectService.getProjectById(id);
    }
    @PostMapping("")
    public Project createProject(@RequestBody ProjectRequest projectRequest){
        return projectService.createProject(projectRequest);
    }

    @PutMapping("")
    public Project updateProject(@RequestBody ProjectRequest projectRequest){
        return projectService.updateProject(projectRequest.getId(),projectRequest.getName());
    }
    @PutMapping("addEmployees")
    public String addEmployees(@RequestBody ProjectRequest projectRequest){
        return projectService.addEmployees(projectRequest.getId(),projectRequest.getEmployeeIds());
    }
    @DeleteMapping
    public String deleteProject(@RequestParam("id") Long id){
        return projectService.deleteProjectById(id);
    }
}
