package com.hyperface.employeemanagementsystem.models.dtos

import groovy.transform.TupleConstructor
import jakarta.validation.constraints.NotEmpty;


@TupleConstructor
class DepartmentRequest {
    @NotEmpty(message = "The department name cannot be empty")
    String name;
}
