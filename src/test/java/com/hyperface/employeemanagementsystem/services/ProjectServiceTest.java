package com.hyperface.employeemanagementsystem.services;


import com.hyperface.employeemanagementsystem.models.Department;
import com.hyperface.employeemanagementsystem.models.Project;
import com.hyperface.employeemanagementsystem.models.dtos.ProjectRequest;
import com.hyperface.employeemanagementsystem.repositories.ProjectRepository;
import com.hyperface.employeemanagementsystem.services.impl.ProjectServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {
    @Mock
    ProjectRepository projectRepository;
    @Mock DepartmentService departmentService;
    @Mock EmployeeService employeeService;
    @InjectMocks ProjectServiceImpl projectService;
    private ProjectRequest projectRequest;
    private Project project;
    private Long projectId;
    @BeforeEach
    public void setUpData(){
        projectRequest=new ProjectRequest();
        projectRequest.setName("Employee Management System");
        project=new Project();
        project.setName("Employee Management System");
        projectId=1L;
        project.setId(projectId);
    }

    @Test
    public void ProjectService_GetAllProjects_ReturnsProjectList(){
        List<Project> projectList=new ArrayList<>();
        projectList.add(project);
        when(projectRepository.findAll()).thenReturn(projectList);
        List<Project> savedProjectList=projectService.getAllProjects();
        Assertions.assertEquals(1,savedProjectList.size());
    }
    @Test
    public void ProjectService_GetProjectById_ReturnsProject(){
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        Project savedProject=projectService.getProjectById(projectId);
        Assertions.assertNotNull(savedProject);
    }
    @Test
    public void ProjectService_AccessingProjectNotPresent_ThrowsException(){
        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());
        Assertions.assertThrows(
                EntityNotFoundException.class,
                ()->projectService.getProjectById(projectId)
        );
    }

    @Test
    public void ProjectService_CreateProject_ReturnsProject(){
        Long departmentId=1L;
        projectRequest.setDepartmentId(departmentId);
        Department department=new Department("OMS");
        department.setId(departmentId);
        when(departmentService.getDepartmentById(departmentId)).thenReturn(department);
        when(projectRepository.save(any(Project.class))).then(returnsFirstArg());
        Project savedProject=projectService.createProject(projectRequest);
        Assertions.assertNotNull(savedProject);
        Assertions.assertEquals(departmentId,savedProject.getDepartment().getId());
    }

    @Test
    public void ProjectService_UpdateProject_ReturnsProject(){
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(projectRepository.save(any(Project.class))).then(returnsFirstArg());
        Project savedProject=projectService.updateProject(projectId,"Cyber");
        Assertions.assertNotNull(savedProject);
        Assertions.assertEquals("Cyber",savedProject.getName());
    }

    @Test
    public void ProjectService_DeleteProject_ReturnVoid(){
        projectService.deleteProjectById(projectId);
        verify(projectRepository,times(1)).deleteById(projectId);
    }

    @Test
    public void ProjectService_AddEmployees_ReturnsString(){
        List<Long> employeeId=List.of(1L,2L);
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        String answer=projectService.addEmployees(projectId,employeeId);
        verify(employeeService).assignProject(employeeId.get(0),project);
        verify(employeeService).assignProject(employeeId.get(1),project);
        Assertions.assertEquals("Success",answer);
    }
    @Test
    public void ProjectService_RemoveEmployee_ReturnsString(){
        Long employeeId=2L;
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        String answer=projectService.removeEmployee(projectId,employeeId);
        verify(employeeService,times(1)).unAssignProject(employeeId,project);
        Assertions.assertEquals("Successfully removed",answer);
    }

}
