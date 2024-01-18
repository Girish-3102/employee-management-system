package com.example.employeemanagementsystem.repositories;

import com.example.employeemanagementsystem.models.Department;
import com.example.employeemanagementsystem.models.Project;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ProjectRepositoryTest {
    private final ProjectRepository projectRepository;
    private Project project;
    @Autowired
    public ProjectRepositoryTest(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }
    @BeforeEach
    public void setUpData(){
        project=new Project();
        project.setName("Security");
    }

    @Test
    public void ProjectRepositoryTest_CreateProject_ReturnsSavedProject(){
        Project savedProject=projectRepository.save(project);
        Assertions.assertNotNull(savedProject);
    }
    @Test
    public void ProjectRepositoryTest_FindProjectById_ReturnsSavedProject(){
        Project savedProject=projectRepository.save(project);
        Project retrievedProject=projectRepository.findById(savedProject.getId()).get();
        Assertions.assertNotNull(retrievedProject);
        Assertions.assertEquals(savedProject.getId(),retrievedProject.getId());
    }
    @Test
    public void ProjectRepositoryTest_FindAll_ReturnsProjectList(){
        Project project1=new Project();
        projectRepository.save(project);
        projectRepository.save(project1);
        List<Project> projectList=projectRepository.findAll();
        Assertions.assertNotNull(projectList);
        Assertions.assertEquals(2,projectList.size());
    }

    @Test
    public void ProjectRepositoryTest_UpdateProject_ReturnsSavedProject(){
        Project savedProject=projectRepository.save(project);
        savedProject.setName("Cyber");
        Project updatedProject=projectRepository.save(savedProject);
        Assertions.assertNotNull(updatedProject);
        Assertions.assertEquals(savedProject.getId(),updatedProject.getId());
        Assertions.assertEquals("Cyber",updatedProject.getName());
    }
    @Test
    public void ProjectRepository_DeleteProject(){
        Project savedProject=projectRepository.save(project);
        projectRepository.deleteById(savedProject.getId());
        Optional<Project> deletedProject=projectRepository.findById(savedProject.getId());
        Assertions.assertEquals(Optional.empty(),deletedProject);
    }
}
