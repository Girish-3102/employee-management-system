package com.example.employeemanagementsystem.models.mappers;

import com.example.employeemanagementsystem.models.Employee;
import com.example.employeemanagementsystem.models.dtos.EmployeeRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEmployeeFromDto(EmployeeRequest employeeRequest, @MappingTarget Employee entity);
}