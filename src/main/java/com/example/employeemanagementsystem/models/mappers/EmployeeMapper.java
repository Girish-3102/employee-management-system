package com.example.employeemanagementsystem.models.mappers;

import com.example.employeemanagementsystem.models.Employee;
import com.example.employeemanagementsystem.models.dtos.EmployeeDto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEmployeeFromDto(EmployeeDto dto, @MappingTarget Employee entity);
}