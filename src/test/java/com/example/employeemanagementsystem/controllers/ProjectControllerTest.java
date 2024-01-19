package com.example.employeemanagementsystem.controllers;

import com.example.employeemanagementsystem.services.ProjectService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


@ExtendWith(SpringExtension.class)
@WebMvcTest(ProjectController.class)
public class ProjectControllerTest {
    @Autowired MockMvc mockMvc;
    @MockBean ProjectService projectService;
}
