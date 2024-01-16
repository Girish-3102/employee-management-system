package com.example.employeemanagementsystem.repositories;
import com.example.employeemanagementsystem.models.Department;
import org.assertj.core.api.Assertions;
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
    @Autowired private DepartmentRepository departmentRepository;

    @Test
    public void DepartmentRepository_SaveDepartment_ReturnsSavedDepartment(){
        Department department=new Department("OMS");
        departmentRepository.save(department);
        Assertions.assertThat(department).isNotNull();
        Assertions.assertThat(department.getId()).isGreaterThan(0);
    }

    @Test
    public void DepartmentRepository_GetAllDepartments_ReturnsDepartmentList(){
        Department department=new Department("OMS");
        Department department2=new Department("TENET");
        departmentRepository.save(department);
        departmentRepository.save(department2);
        List<Department> departmentList=departmentRepository.findAll();
        Assertions.assertThat(departmentList).isNotNull();
        Assertions.assertThat(departmentList.size()).isEqualTo(2);
    }
    @Test
    public void DepartmentRepository_GetDepartmentById_ReturnsDepartment(){
        Department department=new Department("OMS");
        departmentRepository.save(department);
        Department savedDepartment=departmentRepository.findById(department.getId()).get();
        Assertions.assertThat(savedDepartment).isNotNull();
    }
    @Test
    public void DepartmentRepository_UpdateDepartmentById_ReturnsDepartment(){
        Department department=new Department("OMS");
        departmentRepository.save(department);
        Department savedDepartment=departmentRepository.findById(department.getId()).get();
        savedDepartment.setName("Doraemon");
        Department updatedDepartment=departmentRepository.save(savedDepartment);
        Assertions.assertThat(updatedDepartment.getName()).isEqualTo("Doraemon");
    }
    @Test
    public void DepartmentRepository_DeleteDepartment(){
        Department department=new Department("OMS");
        Department savedDepartment=departmentRepository.save(department);
        departmentRepository.deleteById(savedDepartment.getId());
        Optional<Department> optionalDepartment=departmentRepository.findById(savedDepartment.getId());
        Assertions.assertThat(optionalDepartment).isEmpty();
    }
}
