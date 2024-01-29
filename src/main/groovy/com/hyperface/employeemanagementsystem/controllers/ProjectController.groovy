package com.hyperface.employeemanagementsystem.controllers;


import com.hyperface.employeemanagementsystem.models.Project;
import com.hyperface.employeemanagementsystem.models.dtos.ProjectRequest;
import com.hyperface.employeemanagementsystem.services.ProjectService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/project")
class ProjectController {
    ProjectService projectService;
    ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }
    @GetMapping("")
    List<Project> getAllProjects(){
        return projectService.getAllProjects();
    }
    @GetMapping("/{id}")
    Project getProjectById(@PathVariable("id") Long id){
        return projectService.getProjectById(id);
    }
    @PostMapping("")
    Project createProject(@Valid @RequestBody ProjectRequest projectRequest){
        return projectService.createProject(projectRequest);
    }

    @PutMapping("")
    Project updateProject(@RequestParam("id")Long id,@RequestBody ProjectRequest projectRequest){
        return projectService.updateProject(id,projectRequest.getName());
    }
    @PutMapping("addEmployees")
    String addEmployees(@RequestParam("id")Long id,@RequestBody ProjectRequest projectRequest){
        return projectService.addEmployees(id,projectRequest.getEmployeeIds());
    }
    @PatchMapping("/removeEmployee")
    String removeEmployee(@RequestParam("id")Long id,@RequestParam("employeeId")Long employeeId){
        return projectService.removeEmployee(id,employeeId);
    }
    @DeleteMapping
    String deleteProject(@RequestParam("id") Long id){
        return projectService.deleteProjectById(id);
    }
}
