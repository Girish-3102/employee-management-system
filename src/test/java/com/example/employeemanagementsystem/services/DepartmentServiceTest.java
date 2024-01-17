package com.example.employeemanagementsystem.services;

import com.example.employeemanagementsystem.models.Department;
import com.example.employeemanagementsystem.models.dtos.DepartmentRequest;
import com.example.employeemanagementsystem.repositories.DepartmentRepository;
import com.example.employeemanagementsystem.services.impl.DepartmentServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {
    @Mock
    private DepartmentRepository departmentRepository;
    @InjectMocks
    private DepartmentServiceImpl departmentService;

    private DepartmentRequest departmentRequest;
    @BeforeEach
    void setUpData(){
        departmentRequest=new DepartmentRequest("OMS");
    }
    @Test
    public void DepartmentService_CreateDepartment_ReturnsDepartment(){
        Department department=new Department(departmentRequest.getName());
        when(departmentRepository.save(Mockito.any(Department.class))).thenReturn(department);

        Department savedDepartment=departmentService.createDepartment(departmentRequest);

        Assertions.assertNotNull(savedDepartment);
        Assertions.assertEquals(savedDepartment.getName(),departmentRequest.getName());
    }

    @Test
    public void DepartmentService_GetAllDepartments_ReturnsDepartmentList(){
        Department department=new Department(departmentRequest.getName());
        Department department1=new Department("TENET");
        List<Department> departmentList=new ArrayList<>();
        departmentList.add(department1);
        departmentList.add(department);

        when(departmentRepository.findAll()).thenReturn(departmentList);
        List<Department> savedDepartmentList=departmentService.getAllDepartments();

        Assertions.assertNotNull(savedDepartmentList);
    }

    @Test
    public void DepartmentService_GetDepartmentById_ReturnsDepartment(){
        Long departmentId=1L;
        Department department=new Department(departmentRequest.getName());
        department.setId(departmentId);
        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(department));
        Department savedDepartment=departmentService.getDepartmentById(departmentId);
        Assertions.assertNotNull(savedDepartment);
    }
    @Test
    public void DepartmentService_AccessingDepartmentNotPresent_ThrowsException(){
        Long departmentId=1L;
        when(departmentRepository.findById(departmentId)).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class,()->departmentService.getDepartmentById(departmentId));
    }

    @Test
    public void DepartmentService_UpdateDepartment_ReturnsDepartment(){
        Long departmentId=1L;
        Department department=new Department("TENET");
        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(department));
        when(departmentRepository.save(Mockito.any(Department.class))).thenReturn(department);
        Department updatedDepartment=departmentService.updateDepartmentName(departmentId,"OMS");
        Assertions.assertNotNull(updatedDepartment);
        Assertions.assertEquals("OMS",updatedDepartment.getName());
    }

    @Test
    public void DepartmentService_DeleteDepartment(){
        Long departmentId=1L;
        departmentService.deleteDepartmentById(departmentId);
        verify(departmentRepository,times(1)).deleteById(departmentId);
    }
}
