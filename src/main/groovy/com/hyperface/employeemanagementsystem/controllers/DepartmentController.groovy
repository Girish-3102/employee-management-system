package com.hyperface.employeemanagementsystem.controllers;

import com.hyperface.employeemanagementsystem.models.Department;
import com.hyperface.employeemanagementsystem.models.dtos.DepartmentRequest;
import com.hyperface.employeemanagementsystem.services.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
class DepartmentController {
    DepartmentService departmentService;

    DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("")
    List<Department> getAllDepartments(){
        return departmentService.getAllDepartments();
    }

    @GetMapping("/{id}")
    Department getDepartmentById(@PathVariable("id") Long id){
        return departmentService.getDepartmentById(id);
    }
    @PostMapping("")
    Department createDepartment(@Valid @RequestBody DepartmentRequest departmentRequest){
        return departmentService.createDepartment(departmentRequest);
    }
    @PutMapping
    Department updateDepartment(@RequestParam("id") Long id,@Valid @RequestBody DepartmentRequest departmentRequest){
        return departmentService.updateDepartmentName(id,departmentRequest.getName());
    }
    @DeleteMapping
    String deleteDepartmentById(@RequestParam("id") Long id){
        return departmentService.deleteDepartmentById(id);
    }
}
