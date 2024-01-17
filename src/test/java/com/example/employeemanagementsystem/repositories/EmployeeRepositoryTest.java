package com.example.employeemanagementsystem.repositories;


import com.example.employeemanagementsystem.models.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class EmployeeRepositoryTest {
    private final EmployeeRepository employeeRepository;
    @Autowired
    public EmployeeRepositoryTest(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Test
    public void EmploymentRepository_SaveEmployee_ReturnsSavedEmployee(){


    }

    @Test
    public void EmploymentRepository_GetEmployees_ReturnsEmployeeList(){

    }

    @Test
    public void EmploymentRepository_GetEmployeeById_ReturnsEmployee(){

    }

    @Test
    public void EmploymentRepository_UpdateEmployee_ReturnsEmployee(){

    }
    @Test
    public void EmploymentRepository_AssignProject_ReturnsEmployee(){

    }

    @Test
    public void EmploymentRepository_UnAssignProject_ReturnsEmployee(){

    }

    @Test
    public void EmploymentRepository_DeleteEmployee(){

    }


}
