package com.hyperface.employeemanagementsystem.controllers;
import com.hyperface.employeemanagementsystem.exceptions.DifferentDepartmentException;
import com.hyperface.employeemanagementsystem.models.Project;
import com.hyperface.employeemanagementsystem.models.dtos.ProjectRequest;
import com.hyperface.employeemanagementsystem.services.ProjectService;
import com.hyperface.employeemanagementsystem.services.impl.JwtService;
import com.hyperface.employeemanagementsystem.utils.TestUtils;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(ProjectController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ProjectControllerTest {
    @Autowired MockMvc mockMvc;
    @MockBean ProjectService projectService;

    @MockBean JwtService jwtService;
    private ProjectRequest projectRequest;
    private Project project;
    private Long projectId;
    @BeforeEach
    public void setUpData(){
        projectId=1L;
        projectRequest=new ProjectRequest();
        projectRequest.setDepartmentId(1L);
        projectRequest.setName("Employee ManagementSystem");
        project=new Project();
        project.setId(projectId);
        project.setName(projectRequest.getName());
    }

    @Test
    public void ProjectController_GetAllProjects_ReturnsProjectListJson() throws Exception{
        when(projectService.getAllProjects()).thenReturn(List.of(project));
        mockMvc.perform(get("/project").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name",is(project.getName())));
    }
    @Test
    public void ProjectController_GetProjectById_ReturnsProjectJson() throws Exception{
        when(projectService.getProjectById(projectId)).thenReturn(project);
        mockMvc.perform(get("/project/{id}",projectId).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is(project.getName())));
    }

    @Test
    public void ProjectController_GetProjectByIdNotPresent_ReturnsNotFound() throws Exception{
        when(projectService.getProjectById(projectId)).thenThrow(EntityNotFoundException.class);
        mockMvc.perform(get("/project/{id}",projectId).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void ProjectController_CreateProject_ReturnsProjectJson() throws Exception{
        when(projectService.createProject(refEq(projectRequest))).thenReturn(project);
        mockMvc.perform(post("/project")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.convertObjectToJson(projectRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is(projectRequest.getName())));
    }

    @Test
    public void ProjectController_CreateProjectWithInvalidName_ReturnsBadRequest() throws Exception{
        projectRequest.setName(null);
        mockMvc.perform(post("/project")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.convertObjectToJson(projectRequest)))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void ProjectController_CreateProjectWithInvalidDepartmentId_ReturnsBadRequest() throws Exception{
        projectRequest.setDepartmentId(null);
        mockMvc.perform(post("/project")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.convertObjectToJson(projectRequest)))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void ProjectController_CreateProjectInWhichDepartmentNotPresent_ReturnsNotFound() throws Exception{
        when(projectService.createProject(refEq(projectRequest))).thenThrow(EntityNotFoundException.class);
        mockMvc.perform(post("/project")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.convertObjectToJson(projectRequest)))
                .andExpect(status().isNotFound());

    }
    @Test
    public void ProjectController_PutProject_ReturnsProjectJson() throws Exception{
        when(projectService.updateProject(projectId,projectRequest.getName())).thenReturn(project);
        mockMvc.perform(put("/project")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id", String.valueOf(projectId))
                        .content(TestUtils.convertObjectToJson(projectRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is(projectRequest.getName())));
    }
    @Test
    public void ProjectController_PutProjectNotPresent_ReturnsNotFound() throws Exception{
        when(projectService.updateProject(projectId,projectRequest.getName())).thenThrow(EntityNotFoundException.class);
        mockMvc.perform(put("/project")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id", String.valueOf(projectId))
                        .content(TestUtils.convertObjectToJson(projectRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void ProjectController_AddEmployeesButProjectOrEmployeeNotPresent_ReturnsNotFound() throws Exception{
        List<Long> employeeIds=new ArrayList<>(List.of(1L,2L));
        projectRequest.setEmployeeIds(employeeIds);
        when(projectService.addEmployees(projectId,employeeIds)).thenThrow(EntityNotFoundException.class);
        mockMvc.perform(put("/project/addEmployees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id", String.valueOf(projectId))
                        .content(TestUtils.convertObjectToJson(projectRequest)))
                .andExpect(status().isNotFound());
    }
    @Test
    public void ProjectController_AddEmployeesButDifferentDepartment_ReturnsNotFound() throws Exception{
        List<Long> employeeIds=new ArrayList<>(List.of(1L,2L));
        projectRequest.setEmployeeIds(employeeIds);
        when(projectService.addEmployees(projectId,employeeIds)).thenThrow(DifferentDepartmentException.class);
        mockMvc.perform(put("/project/addEmployees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id", String.valueOf(projectId))
                        .content(TestUtils.convertObjectToJson(projectRequest)))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void ProjectController_AddEmployees_ReturnsString() throws Exception{
        List<Long> employeeIds=new ArrayList<>(List.of(1L,2L));
        projectRequest.setEmployeeIds(employeeIds);
        when(projectService.addEmployees(projectId,employeeIds)).thenReturn("Success");
        mockMvc.perform(put("/project/addEmployees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id", String.valueOf(projectId))
                        .content(TestUtils.convertObjectToJson(projectRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",is("Success")));
    }
    @Test
    public void ProjectController_RemoveEmployee_ReturnsString() throws Exception{
        Long employeeId=1L;
        when(projectService.removeEmployee(projectId,employeeId)).thenReturn("Successfully Removed");
        mockMvc.perform(patch("/project/removeEmployee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id", String.valueOf(projectId))
                        .param("employeeId",String.valueOf(employeeId))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",is("Successfully Removed")));
    }
    @Test
    public void ProjectController_DeleteProject_ReturnsString() throws Exception{
        when(projectService.deleteProjectById(projectId)).thenReturn("Success");
        mockMvc.perform(delete("/project")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id", String.valueOf(projectId))
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$",is("Success")));
    }
}
