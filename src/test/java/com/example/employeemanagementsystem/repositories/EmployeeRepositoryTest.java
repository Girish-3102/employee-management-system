package com.example.employeemanagementsystem.repositories;


import com.example.employeemanagementsystem.models.Department;
import com.example.employeemanagementsystem.models.Employee;
import com.example.employeemanagementsystem.models.Project;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class EmployeeRepositoryTest {
    private final EmployeeRepository employeeRepository;
    private Employee employee;
    @Autowired
    public EmployeeRepositoryTest(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
    @BeforeEach
    public void setUpData(){
        employee=new Employee();
        employee.setFirstName("Manish");
        employee.setLastName("KB");
    }

    @Test
    public void EmploymentRepository_SaveEmployee_ReturnsSavedEmployee(){
        Employee savedEmployee=employeeRepository.save(employee);
        Assertions.assertNotNull(savedEmployee);
    }

    @Test
    public void EmploymentRepository_GetEmployees_ReturnsEmployeeList(){
        Employee employee1=new Employee();
        employee1.setFirstName("Harish");
        employee1.setLastName("N");
        employeeRepository.save(employee);
        employeeRepository.save(employee1);
        List<Employee> employeeList=employeeRepository.findAll();
        Assertions.assertNotNull(employeeList);
        Assertions.assertEquals(2,employeeList.size());
    }

    @Test
    public void EmploymentRepository_GetEmployeeById_ReturnsEmployee(){
        Employee savedEmployee=employeeRepository.save(employee);
        Employee retrievedEmployee=employeeRepository.findById(savedEmployee.getId()).get();
        Assertions.assertNotNull(retrievedEmployee);
        Assertions.assertEquals(savedEmployee.getId(),retrievedEmployee.getId());
    }

    @Test
    public void EmploymentRepository_UpdateEmployee_ReturnsEmployee(){
        Employee savedEmployee=employeeRepository.save(employee);
        savedEmployee.setFirstName("Harish");
        savedEmployee.setLastName("M");
        savedEmployee.setDepartment(new Department("OMS"));
        savedEmployee.setProject(new HashSet<>(List.of(new Project())));
        Employee updatedEmployee=employeeRepository.save(savedEmployee);
        Assertions.assertEquals(savedEmployee.getId(),updatedEmployee.getId());
        Assertions.assertEquals("Harish",updatedEmployee.getFirstName());
        Assertions.assertEquals("M",updatedEmployee.getLastName());
        Assertions.assertEquals("OMS",updatedEmployee.getDepartment().getName());
        Assertions.assertEquals(1,updatedEmployee.getProject().size());
    }

    @Test
    public void EmploymentRepository_DeleteEmployee(){
        Employee savedEmployee=employeeRepository.save(employee);
        employeeRepository.deleteById(savedEmployee.getId());
        Optional<Employee> deletedEmployee=employeeRepository.findById(savedEmployee.getId());
        Assertions.assertEquals(Optional.empty(),deletedEmployee);
    }


}
