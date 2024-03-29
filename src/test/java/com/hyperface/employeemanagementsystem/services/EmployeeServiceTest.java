package com.hyperface.employeemanagementsystem.services;

import com.hyperface.employeemanagementsystem.exceptions.DifferentDepartmentException;
import com.hyperface.employeemanagementsystem.models.*;
import com.hyperface.employeemanagementsystem.models.dtos.EmployeeRequest;
import com.hyperface.employeemanagementsystem.models.mappers.EmployeeMapper;
import com.hyperface.employeemanagementsystem.repositories.EmployeeRepository;
import com.hyperface.employeemanagementsystem.security.SecurityUtils;
import com.hyperface.employeemanagementsystem.services.impl.EmployeeServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

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

    private EmployeeRequest employeeRequest;
    private Department department;
    private Employee employee;
    private Long employeeId;
    private Long departmentId;

    @BeforeEach
    public void setUpData(){
        employeeRequest=new EmployeeRequest();
        employeeRequest.setFirstName("Manish");
        employeeRequest.setLastName("KB");
        employeeRequest.setDepartmentId(1L);
        employeeId=1L;
        departmentId=1L;
        department=new Department("OMS");
        department.setId(departmentId);
        employee=new Employee();
        employee.setFirstName("Manish");
        employee.setLastName("KB");
        employee.setId(employeeId);
    }

    @Test
    public void EmployeeService_CreateEmployee_ReturnsEmployee(){
        when(departmentService.getDepartmentById(departmentId)).thenReturn(department);
        when(employeeRepository.save(any())).then(returnsFirstArg());
        Employee savedEmployee=employeeService.createEmployee(employeeRequest);
        Assertions.assertNotNull(savedEmployee);
        Assertions.assertEquals(employeeRequest.getFirstName(),savedEmployee.getFirstName());
        Assertions.assertEquals(employeeRequest.getLastName(),savedEmployee.getLastName());
        Assertions.assertEquals(employeeRequest.getDepartmentId(),savedEmployee.getDepartment().getId());
    }

    @Test
    public void EmployeeService_GetAllEmployees_ReturnsEmployeeList(){
        List<Employee> employeeList=new ArrayList<>();
        employeeList.add(employee);
        when(employeeRepository.findAll()).thenReturn(employeeList);
        List<Employee> savedEmployeeList=employeeService.getAllEmployees();
        Assertions.assertEquals(1,savedEmployeeList.size());
    }

    @Test
    public void EmployeeService_GetEmployeeById_ReturnsEmployee(){
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        Employee savedEmployee=employeeService.getEmployeeById(employeeId);
        Assertions.assertNotNull(savedEmployee);
        Assertions.assertEquals(employeeId,savedEmployee.getId());
    }

    @Test
    public void EmployeeService_AccessEmployeeNotPresent_ThrowsException(){
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class,()->{
            employeeService.getEmployeeById(employeeId);
        });
    }

    @Test
    public void EmployeeService_UpdateEmployeeWithoutDepartment_ReturnsEmployee(){
        employeeRequest.setFirstName("Harish");
        employeeRequest.setLastName("N");
        employeeRequest.setDepartmentId(null);
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).then(returnsFirstArg());
        Employee savedEmployee=employeeService.updateEmployee(employeeId,employeeRequest);
        Assertions.assertNotNull(savedEmployee);
        Assertions.assertEquals(employeeId,savedEmployee.getId());
        Assertions.assertEquals(employeeRequest.getFirstName(),savedEmployee.getFirstName());
        Assertions.assertEquals(employeeRequest.getLastName(),savedEmployee.getLastName());
    }

    @Test
    public void EmployeeService_UpdateDepartment_ReturnsEmployee(){
        Long departmentIdToBeUpdated=2L;
        Department departmentToBeUpdated=new Department("TENET");
        departmentToBeUpdated.setId(departmentIdToBeUpdated);
        employee.setDepartment(department);
        employee.setProject(new HashSet<>(List.of(new Project("Dell",department))));
        when(departmentService.getDepartmentById(departmentIdToBeUpdated)).thenReturn(departmentToBeUpdated);
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).then(returnsFirstArg());

        Employee savedEmployee=employeeService.updateDepartment(employeeId,departmentIdToBeUpdated);

        Assertions.assertNotNull(savedEmployee);
        Assertions.assertEquals(employeeId,savedEmployee.getId());
        Assertions.assertEquals(departmentIdToBeUpdated,savedEmployee.getDepartment().getId());
        Assertions.assertEquals(0,savedEmployee.getProject().size());
    }

    @Test
    public void EmployeeService_DeleteEmployee_ReturnsVoid(){
        employeeService.deleteEmployee(employeeId);
        verify(employeeRepository,times(1)).deleteById(employeeId);
    }

    @Test
    public void EmployeeService_AssignProject_ReturnsEmployee(){
        employee.setDepartment(department);
        employee.setProject(new HashSet<>(List.of(new Project("Employee Management",department))));
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any())).then(returnsFirstArg());
        Project project=new Project("Security",department);
        Employee savedEmployee=employeeService.assignProject(employeeId,project);
        Assertions.assertNotNull(savedEmployee);
        Assertions.assertEquals(2,savedEmployee.getProject().size());
        Assertions.assertTrue(savedEmployee.getProject().contains(project));
    }

    @Test
    public void EmployeeService_AssignProjectWithDifferentDepartment_ThrowsException(){
        employee.setDepartment(department);
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        Department differentDepartment=new Department("IMS");
        differentDepartment.setId(2L);
        Project project=new Project("Security",differentDepartment);
        Assertions.assertThrows(DifferentDepartmentException.class,()->employeeService.assignProject(employeeId,project));
    }
    @Test
    public void EmployeeService_UnAssignProject_ReturnsEmployee(){
        employee.setDepartment(department);
        Project existingProject=new Project("Security",department);
        employee.setProject(new HashSet<>(List.of(existingProject)));

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any())).then(returnsFirstArg());

        Employee savedEmployee=employeeService.unAssignProject(employeeId,existingProject);
        Assertions.assertNotNull(savedEmployee);
        Assertions.assertFalse(savedEmployee.getProject().contains(existingProject));
        Assertions.assertEquals(0,savedEmployee.getProject().size());
    }
}
