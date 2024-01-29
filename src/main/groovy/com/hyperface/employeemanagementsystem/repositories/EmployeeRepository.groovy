package com.hyperface.employeemanagementsystem.repositories;

import com.hyperface.employeemanagementsystem.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface EmployeeRepository extends JpaRepository<Employee,Long> {
}
