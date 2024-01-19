package com.example.employeemanagementsystem.controllers;

import com.example.employeemanagementsystem.models.Department;
import com.example.employeemanagementsystem.models.dtos.DepartmentRequest;
import com.example.employeemanagementsystem.services.DepartmentService;
import com.example.employeemanagementsystem.utils.TestUtils;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.CoreMatchers.is;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DepartmentController.class)
public class DepartmentControllerTest {
    @Autowired MockMvc mockMvc;

    @MockBean DepartmentService departmentService;

    private Department department;
    private DepartmentRequest departmentRequest;
    private Long departmentId;

    @BeforeEach
    public void setUpData(){
        departmentRequest=new DepartmentRequest("OMS");
        departmentId=1L;
        department=new Department("OMS");
        department.setId(departmentId);
    }

    @Test
    public void DepartmentControllerTest_GetAllDepartments_ReturnsDepartmentListJson() throws Exception{
        when(departmentService.getAllDepartments()).thenReturn(List.of(department));
        mockMvc.perform(get("/department")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(department.getName())));
    }

    @Test
    public void DepartmentControllerTest_GetDepartmentById_ReturnsDepartmentJson() throws Exception{
        when(departmentService.getDepartmentById(departmentId)).thenReturn(department);
        mockMvc.perform(get("/department/{id}",departmentId).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is(department.getName())));
    }

    @Test
    public void DepartmentControllerTest_GetDepartmentNotPresent_ReturnsNotFound() throws Exception{
        when(departmentService.getDepartmentById(departmentId)).thenThrow(EntityNotFoundException.class);
        mockMvc.perform(get("/department/{id}",departmentId).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void DepartmentControllerTest_PostDepartment_ReturnsDepartmentJson() throws Exception{
        when(departmentService.createDepartment(any(DepartmentRequest.class))).thenReturn(department);
        mockMvc.perform(post("/department")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.convertObjectToJson(departmentRequest))
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is(departmentRequest.getName())));
    }

    @Test
    public void DepartmentControllerTest_PostDepartmentWithInvalidName_ReturnsBadRequest() throws Exception{
        mockMvc.perform(post("/department")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
                ).andExpect(status().isBadRequest());
    }

    @Test
    public void DepartmentControllerTest_PutDepartment_ReturnsDepartmentJson() throws Exception{
        department.setName("CMS");
        when(departmentService.updateDepartmentName(eq(departmentId),anyString())).thenReturn(department);
        departmentRequest.setName("CMS");
        mockMvc.perform(put("/department")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id", String.valueOf(departmentId))
                        .content(TestUtils.convertObjectToJson(departmentRequest))
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name",is(departmentRequest.getName())));
        ;
    }
    @Test
    public void DepartmentControllerTest_PutDepartmentNotPresent_ReturnsNotFound() throws Exception{
        when(departmentService.updateDepartmentName(eq(departmentId),any())).thenThrow(EntityNotFoundException.class);
        mockMvc.perform(put("/department")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id", String.valueOf(departmentId))
                        .content(TestUtils.convertObjectToJson(departmentRequest))
                ).andExpect(status().isNotFound());
    }
    @Test
    public void DepartmentControllerTest_PutDepartmentWithInvalidName_ReturnsDepartmentJson() throws Exception{
        mockMvc.perform(put("/department")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", String.valueOf(departmentId))
                .content("{}")
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void DepartmentControllerTest_DeleteDepartment_ReturnsString() throws Exception{
        when(departmentService.deleteDepartmentById(eq(departmentId))).thenReturn("Success");
        mockMvc.perform(delete("/department")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id", String.valueOf(departmentId))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",is("Success")));
    }
}
