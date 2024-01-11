package com.example.employeemanagementsystem.controllers;

import com.example.employeemanagementsystem.models.Employee;
import com.example.employeemanagementsystem.models.dtos.EmployeeRequest;
import com.example.employeemanagementsystem.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("")
    public List<Employee> getAllEmployees(){
        return employeeService.getAllEmployees();
    }
    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable(name = "id") Long id){
            return employeeService.getEmployeeById(id);
    }
    @PostMapping("")
    public Employee createEmployee(@Valid @RequestBody EmployeeRequest employeeRequest){
        return employeeService.createEmployee(employeeRequest);
    }
    @PutMapping("")
    public Employee updateEmployee(@RequestParam("id")Long id,@RequestBody EmployeeRequest employeeRequest){
        return employeeService.updateEmployee(id,employeeRequest);
    }
    @DeleteMapping("")
    public String deleteEmployee(@RequestParam("id") Long id){
        return employeeService.deleteEmployee(id);
    }
}
