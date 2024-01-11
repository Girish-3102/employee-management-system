package com.example.employeemanagementsystem.controllers;

import com.example.employeemanagementsystem.models.Department;
import com.example.employeemanagementsystem.models.dtos.DepartmentRequest;
import com.example.employeemanagementsystem.services.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("")
    public List<Department> getAllDepartments(){
        return departmentService.getAllDepartments();
    }

    @GetMapping("/{id}")
    public Department getDepartmentById(@PathVariable("id") Long id){
        return departmentService.getDepartmentById(id);
    }
    @PostMapping("")
    public Department createDepartment(@Valid @RequestBody DepartmentRequest departmentRequest){
        return departmentService.createDepartment(departmentRequest);
    }
    @PutMapping
    public Department updateDepartment(@RequestParam("id") Long id,@Valid @RequestBody DepartmentRequest departmentRequest){
        return departmentService.updateDepartmentName(id,departmentRequest.getName());
    }
    @DeleteMapping
    public String deleteDepartmentById(@RequestParam("id") Long id){
        return departmentService.deleteDepartmentById(id);
    }
}
