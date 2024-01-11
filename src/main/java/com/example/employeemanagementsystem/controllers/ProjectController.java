package com.example.employeemanagementsystem.controllers;


import com.example.employeemanagementsystem.models.Project;
import com.example.employeemanagementsystem.models.dtos.ProjectRequest;
import com.example.employeemanagementsystem.services.ProjectService;
import jakarta.validation.Valid;
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
    public Project createProject(@Valid @RequestBody ProjectRequest projectRequest){
        return projectService.createProject(projectRequest);
    }

    @PutMapping("")
    public Project updateProject(@RequestParam("id")Long id,@RequestBody ProjectRequest projectRequest){
        return projectService.updateProject(id,projectRequest.getName());
    }
    @PutMapping("addEmployees")
    public String addEmployees(@RequestParam("id")Long id,@RequestBody ProjectRequest projectRequest){
        return projectService.addEmployees(id,projectRequest.getEmployeeIds());
    }
    @PatchMapping("/removeEmployee")
    public String removeEmployee(@RequestParam("id")Long id,@RequestParam("employeeId")Long employeeId){
        return projectService.removeEmployee(id,employeeId);
    }
    @DeleteMapping
    public String deleteProject(@RequestParam("id") Long id){
        return projectService.deleteProjectById(id);
    }
}
