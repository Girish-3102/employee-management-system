package com.example.employeemanagementsystem.repositories;
import com.example.employeemanagementsystem.models.Department;
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
public class DepartmentRepositoryTest {
    private final DepartmentRepository departmentRepository;
    private Department department;

    @BeforeEach
    public void before(){
        department=new Department("OMS");
    }
    @Autowired
    public DepartmentRepositoryTest(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Test
    public void DepartmentRepository_SaveDepartment_ReturnsSavedDepartment(){
        Department savedDepartment=departmentRepository.save(department);
        Assertions.assertNotNull(savedDepartment);
    }

    @Test
    public void DepartmentRepository_GetAllDepartments_ReturnsDepartmentList(){
        Department department2=new Department("TENET");
        departmentRepository.save(department);
        departmentRepository.save(department2);
        List<Department> departmentList=departmentRepository.findAll();
        Assertions.assertNotNull(departmentList);
        Assertions.assertEquals(2,departmentList.size());
    }
    @Test
    public void DepartmentRepository_GetDepartmentById_ReturnsDepartment(){
        departmentRepository.save(department);
        Department savedDepartment=departmentRepository.findById(department.getId()).get();
        Assertions.assertNotNull(savedDepartment);
    }
    @Test
    public void DepartmentRepository_UpdateDepartmentById_ReturnsDepartment(){
        departmentRepository.save(department);
        Department savedDepartment=departmentRepository.findById(department.getId()).get();
        savedDepartment.setName("Doraemon");
        Department updatedDepartment=departmentRepository.save(savedDepartment);
        Assertions.assertEquals("Doraemon",updatedDepartment.getName());
    }
    @Test
    public void DepartmentRepository_DeleteDepartment(){
        Department savedDepartment=departmentRepository.save(department);
        departmentRepository.deleteById(savedDepartment.getId());
        Optional<Department> optionalDepartment=departmentRepository.findById(savedDepartment.getId());
        Assertions.assertEquals(Optional.empty(),optionalDepartment);
    }

}
