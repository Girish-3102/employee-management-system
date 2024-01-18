package com.example.employeemanagementsystem.services;


import com.example.employeemanagementsystem.models.Department;
import com.example.employeemanagementsystem.models.Employee;
import com.example.employeemanagementsystem.models.Project;
import com.example.employeemanagementsystem.models.dtos.ProjectRequest;
import com.example.employeemanagementsystem.repositories.ProjectRepository;
import com.example.employeemanagementsystem.services.impl.ProjectServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.AdditionalAnswers.returnsSecondArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {
    @Mock ProjectRepository projectRepository;
    @Mock DepartmentService departmentService;
    @Mock EmployeeService employeeService;
    @InjectMocks ProjectServiceImpl projectService;

    @Test
    public void ProjectService_GetAllEmployees_ReturnsEmployeeList(){
        Project project=new Project();
        Project project1=new Project();
        List<Project> projectList=new ArrayList<>();
        projectList.add(project1);
        projectList.add(project);
        when(projectRepository.findAll()).thenReturn(projectList);
        List<Project> savedProjectList=projectService.getAllProjects();
        Assertions.assertEquals(2,savedProjectList.size());
    }
    @Test
    public void ProjectService_GetProjectById_ReturnsProject(){
        Long projectId=1L;
        Project project=new Project("Doraemon",new Department("OMS"));
        project.setId(projectId);
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        Project savedProject=projectService.getProjectById(projectId);
        Assertions.assertNotNull(savedProject);
        Assertions.assertEquals(projectId,savedProject.getId());
    }
    @Test
    public void ProjectService_AccessingProjectNotPresent_ThrowsException(){
        Long projectId=1L;
        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());
        Assertions.assertThrows(
                EntityNotFoundException.class,
                ()->projectService.getProjectById(projectId)
        );
    }

    @Test
    public void ProjectService_CreateProject_ReturnsProject(){
        Long departmentId=1L;
        ProjectRequest projectRequest=new ProjectRequest();
        projectRequest.setDepartmentId(departmentId);
        Department department=new Department("OMS");
        department.setId(departmentId);
        when(departmentService.getDepartmentById(departmentId)).thenReturn(department);
        when(projectRepository.save(any(Project.class))).then(returnsFirstArg());
        Project savedProject=projectService.createProject(projectRequest);
        Assertions.assertNotNull(savedProject);
    }

    @Test
    public void ProjectService_UpdateProject_ReturnsProject(){
        Long projectId=1L;
        Project project=new Project("Doraemon",new Department("OMS"));
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(projectRepository.save(any(Project.class))).then(returnsFirstArg());
        Project savedProject=projectService.updateProject(projectId,"Cyber");
        Assertions.assertNotNull(savedProject);
        Assertions.assertEquals("Cyber",savedProject.getName());
    }

    @Test
    public void ProjectService_DeleteProject_ReturnVoid(){
        Long projectId=1L;
        projectService.deleteProjectById(projectId);
        verify(projectRepository,times(1)).deleteById(projectId);
    }

    @Test
    public void ProjectService_AddEmployees_ReturnsString(){
        Long projectId=1L;
        List<Long> employeeId=List.of(1L,2L);
        Project project=new Project("Security",new Department("TENET"));
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        String answer=projectService.addEmployees(projectId,employeeId);

        verify(employeeService).assignProject(employeeId.get(0),project);
        verify(employeeService).assignProject(employeeId.get(1),project);
        Assertions.assertEquals("Success",answer);
    }
    @Test
    public void ProjectService_RemoveEmployee_ReturnsString(){
        Long projectId=1L,employeeId=2L;
        Project project=new Project("Security",new Department("TENET"));
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        String answer=projectService.removeEmployee(projectId,employeeId);
        verify(employeeService,times(1)).unAssignProject(employeeId,project);
        Assertions.assertEquals("Successfully removed",answer);
    }

}
