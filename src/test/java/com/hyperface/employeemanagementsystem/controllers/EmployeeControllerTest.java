package com.hyperface.employeemanagementsystem.controllers;


import com.hyperface.employeemanagementsystem.models.Employee;
import com.hyperface.employeemanagementsystem.models.dtos.EmployeeRequest;
import com.hyperface.employeemanagementsystem.services.EmployeeService;
import com.hyperface.employeemanagementsystem.services.impl.JwtService;
import com.hyperface.employeemanagementsystem.utils.TestUtils;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(EmployeeController.class)
@AutoConfigureMockMvc(addFilters = false)
public class EmployeeControllerTest {
    @Autowired MockMvc mockMvc;
    @MockBean EmployeeService employeeService;
    @MockBean JwtService jwtService;

    private Employee employee;
    private EmployeeRequest employeeRequest;
    private Long employeeId;

    @BeforeEach
    public void setUpData(){
        employee=new Employee();
        employeeId=1L;
        employeeRequest=new EmployeeRequest();
        employee.setFirstName("Manish");
        employee.setLastName("KB");
        employee.setId(employeeId);
        employeeRequest.setDepartmentId(1L);
        employeeRequest.setFirstName("Manish");
        employeeRequest.setLastName("KB");
    }

    @Test
    public void EmployeeController_GetAllEmployees_ReturnsEmployeeListJson() throws Exception{
        when(employeeService.getAllEmployees()).thenReturn(List.of(employee));
        mockMvc.perform(get("/employee")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName",is(employee.getFirstName())));
    }
    @Test
    public void EmployeeController_GetEmployeeById_ReturnsEmployeeJson() throws Exception{
        when(employeeService.getEmployeeById(employeeId)).thenReturn(employee);
        mockMvc.perform(get("/employee/{id}",employeeId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName",is(employee.getFirstName())));
    }

    @Test
    public void EmployeeController_GetEmployeeByIdNotPresent_ReturnsNotFound() throws Exception{
        when(employeeService.getEmployeeById(employeeId)).thenThrow(EntityNotFoundException.class);
        mockMvc.perform(get("/employee/{id}",employeeId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void EmployeeController_PutEmployee_ReturnsEmployeeJson() throws Exception{
        when(employeeService.updateEmployee(eq(employeeId),refEq(employeeRequest))).thenReturn(employee);
        mockMvc.perform(put("/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id", String.valueOf(employeeId))
                        .content(TestUtils.convertObjectToJson(employeeRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName",is(employeeRequest.getFirstName())));
    }

    @Test
    public void EmployeeController_PutEmployeeWhichIsNotPresent_ReturnsNotFound() throws Exception{
        when(employeeService.updateEmployee(eq(employeeId),refEq(employeeRequest))).thenThrow(EntityNotFoundException.class);
        mockMvc.perform(put("/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id", String.valueOf(employeeId))
                        .content(TestUtils.convertObjectToJson(employeeRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void EmployeeController_DeleteEmployee_ReturnsString() throws Exception{
        when(employeeService.deleteEmployee(employeeId)).thenReturn("Success");
        mockMvc.perform(delete("/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id", String.valueOf(employeeId)))
                .andExpect(status().isOk());
    }
}
