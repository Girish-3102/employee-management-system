package com.example.employeemanagementsystem.services;


import com.example.employeemanagementsystem.models.Department;
import com.example.employeemanagementsystem.models.Employee;
import com.example.employeemanagementsystem.models.Project;
import com.example.employeemanagementsystem.models.dtos.EmployeeRequest;
import com.example.employeemanagementsystem.models.mappers.EmployeeMapper;
import com.example.employeemanagementsystem.repositories.EmployeeRepository;
import com.example.employeemanagementsystem.services.impl.EmployeeServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.*;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    @Mock
    private DepartmentService departmentService;
    @Mock
    private EmployeeRepository employeeRepository;
    @Spy
    EmployeeMapper employeeMapper= Mappers.getMapper(EmployeeMapper.class);
    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    public void EmployeeService_CreateEmployee_ReturnsEmployee(){
        EmployeeRequest employeeRequest=new EmployeeRequest();
        employeeRequest.setDepartmentId(1L);
        employeeRequest.setFirstName("Manish");
        employeeRequest.setLastName("KB");
        Department department=new Department("OMS");
        when(departmentService.getDepartmentById(1L)).thenReturn(department);
        when(employeeRepository.save(any())).then(returnsFirstArg());

        Employee savedEmployee=employeeService.createEmployee(employeeRequest);

        Assertions.assertNotNull(savedEmployee);
    }

    @Test
    public void EmployeeService_GetAllEmployees_ReturnsEmployeeList(){
        //Arrange
        Employee employee1=new Employee();
        Employee employee2=new Employee();
        List<Employee> employeeList=new ArrayList<>();
        employeeList.add(employee1);
        employeeList.add(employee2);
        when(employeeRepository.findAll()).thenReturn(employeeList);

        //Act
        List<Employee> savedEmployeeList=employeeService.getAllEmployees();

        //Assert
        Assertions.assertEquals(2,savedEmployeeList.size());
    }

    @Test
    public void EmployeeService_GetEmployeeById_ReturnsEmployee(){
        //Arrange
        Long employeeId=1L;
        Employee employee=new Employee();
        employee.setId(employeeId);
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        //Act
        Employee savedEmployee=employeeService.getEmployeeById(employeeId);
        //Assert
        Assertions.assertNotNull(savedEmployee);
        Assertions.assertEquals(employeeId,savedEmployee.getId());
    }

    @Test
    public void EmployeeService_AccessEmployeeNotPresent_ThrowsException(){
        Long employeeId=1L;
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class,()->{
            employeeService.getEmployeeById(employeeId);
        });
    }

    @Test
    public void EmployeeService_UpdateEmployeeWithoutDepartment_ReturnsEmployee(){
        Long employeeId=1L;
        EmployeeRequest employeeRequest=new EmployeeRequest("Manish","KB",null);
        Employee employee=new Employee();
        employee.setFirstName("Harish");
        employee.setLastName("N");
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).then(returnsFirstArg());
        Employee savedEmployee=employeeService.updateEmployee(employeeId,employeeRequest);
        Assertions.assertNotNull(savedEmployee);
        Assertions.assertEquals(employeeRequest.getFirstName(),savedEmployee.getFirstName());
        Assertions.assertEquals(employeeRequest.getLastName(),savedEmployee.getLastName());
    }

    @Test
    public void EmployeeService_UpdateEmployeeWithDepartment_ReturnsEmployee(){
        Long employeeId=1L;
        Long departmentId=1L;
        EmployeeRequest employeeRequest=new EmployeeRequest("Manish","KB",departmentId);
        Department departmentToBeAssigned=new Department("OMS");
        departmentToBeAssigned.setId(departmentId);
        Employee existingEmployee=new Employee();
        existingEmployee.setFirstName("Harish");
        existingEmployee.setLastName("N");
        existingEmployee.setDepartment(new Department("Tenet"));
        existingEmployee.setProject(new HashSet<>(List.of(new Project("Dell",departmentToBeAssigned))));

        when(departmentService.getDepartmentById(departmentId)).thenReturn(departmentToBeAssigned);
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(existingEmployee));
        when(employeeRepository.save(any(Employee.class))).then(returnsFirstArg());

        Employee savedEmployee=employeeService.updateEmployee(employeeId,employeeRequest);

        Assertions.assertNotNull(savedEmployee);
        Assertions.assertEquals(employeeRequest.getFirstName(),savedEmployee.getFirstName());
        Assertions.assertEquals(employeeRequest.getLastName(),savedEmployee.getLastName());
        Assertions.assertEquals(departmentId,savedEmployee.getDepartment().getId());
        Assertions.assertEquals(0,savedEmployee.getProject().size());
    }

    @Test
    public void EmployeeService_DeleteEmployee_ReturnsVoid(){
        Long employeeId=1L;
        employeeService.deleteEmployee(employeeId);
        verify(employeeRepository,times(1)).deleteById(employeeId);
    }

    @Test
    public void EmployeeService_AssignProject_ReturnsEmployee(){
        Long employeeId=1L;
        Employee existingEmployee=new Employee();
        Department department=new Department("OMS");
        existingEmployee.setFirstName("Harish");
        existingEmployee.setLastName("N");
        existingEmployee.setDepartment(department);
        existingEmployee.setProject(new HashSet<>(List.of(new Project())));

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(existingEmployee));
        when(employeeRepository.save(any())).then(returnsFirstArg());

        Project project=new Project("Security",department);
        Employee savedEmployee=employeeService.assignProject(employeeId,project);
        Assertions.assertNotNull(savedEmployee);
        Assertions.assertEquals(2,savedEmployee.getProject().size());
        Assertions.assertTrue(savedEmployee.getProject().contains(project));
    }
    @Test
    public void EmployeeService_UnAssignProject_ReturnsEmployee(){
        Long employeeId=1L;
        Employee existingEmployee=new Employee();
        Department department=new Department("Tenet");
        existingEmployee.setFirstName("Harish");
        existingEmployee.setLastName("N");
        existingEmployee.setDepartment(department);
        Project existingProject=new Project("Security",department);
        existingEmployee.setProject(new HashSet<>(List.of(existingProject)));

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(existingEmployee));
        when(employeeRepository.save(any())).then(returnsFirstArg());

        Employee savedEmployee=employeeService.unAssignProject(employeeId,existingProject);
        Assertions.assertNotNull(savedEmployee);
        Assertions.assertFalse(savedEmployee.getProject().contains(existingProject));
        Assertions.assertEquals(0,savedEmployee.getProject().size());
    }
}
