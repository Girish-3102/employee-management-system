package com.hyperface.employeemanagementsystem.controllers;

import com.hyperface.employeemanagementsystem.models.Employee;
import com.hyperface.employeemanagementsystem.models.dtos.EmployeeRequest
import com.hyperface.employeemanagementsystem.models.dtos.EmployeeResponse;
import com.hyperface.employeemanagementsystem.services.EmployeeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
class EmployeeController {
    private final EmployeeService employeeService;

    EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("")
    List<Employee> getAllEmployees(){
        return employeeService.getAllEmployees();
    }
    @GetMapping("/{id}")
    EmployeeResponse getEmployeeById(@PathVariable(name = "id") Long id){
        Employee employee = employeeService.getEmployeeById(id);
        return new EmployeeResponse(employee)
    }
    @PutMapping("")
    Employee updateEmployee(@RequestParam("id")Long id,@RequestBody EmployeeRequest employeeRequest){
        return employeeService.updateEmployee(id,employeeRequest)
    }
    @DeleteMapping("")
    String deleteEmployee(@RequestParam("id") Long id){
        return employeeService.deleteEmployee(id);
    }
}
