package com.hyperface.employeemanagementsystem.controllers;

import com.hyperface.employeemanagementsystem.models.Department;
import com.hyperface.employeemanagementsystem.models.Role;
import com.hyperface.employeemanagementsystem.models.UserAuth;
import com.hyperface.employeemanagementsystem.models.dtos.DepartmentRequest;
import com.hyperface.employeemanagementsystem.services.DepartmentService;
import com.hyperface.employeemanagementsystem.services.impl.JwtService;
import com.hyperface.employeemanagementsystem.utils.TestUtils;
import io.jsonwebtoken.Jwts;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.CoreMatchers.is;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DepartmentController.class)
@WithMockUser
public class DepartmentControllerTest {
    @Autowired MockMvc mockMvc;

    @MockBean DepartmentService departmentService;

    @MockBean JwtService jwtService;

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
                .andDo(print())
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
                        .with(csrf())
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is(departmentRequest.getName())));
    }

    @Test
    public void DepartmentControllerTest_PostDepartmentWithInvalidName_ReturnsBadRequest() throws Exception{
        mockMvc.perform(post("/department")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
                        .with(csrf())
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
                        .with(csrf())
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
                        .with(csrf())
                ).andExpect(status().isNotFound());
    }
    @Test
    public void DepartmentControllerTest_PutDepartmentWithInvalidName_ReturnsDepartmentJson() throws Exception{
        mockMvc.perform(put("/department")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", String.valueOf(departmentId))
                .content("{}")
                .with(csrf())
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void DepartmentControllerTest_DeleteDepartment_ReturnsString() throws Exception{
        when(departmentService.deleteDepartmentById(eq(departmentId))).thenReturn("Success");
        mockMvc.perform(delete("/department")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id", String.valueOf(departmentId))
                        .with(csrf())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",is("Success")));
    }
}
