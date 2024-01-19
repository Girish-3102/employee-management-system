package com.hyperface.employeemanagementsystem.models.mappers;

import com.hyperface.employeemanagementsystem.models.Employee;
import com.hyperface.employeemanagementsystem.models.dtos.EmployeeRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEmployeeFromDto(EmployeeRequest employeeRequest, @MappingTarget Employee entity);
}