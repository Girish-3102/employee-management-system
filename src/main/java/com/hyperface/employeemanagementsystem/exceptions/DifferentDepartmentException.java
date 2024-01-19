package com.hyperface.employeemanagementsystem.exceptions;

public class DifferentDepartmentException extends RuntimeException {

    public DifferentDepartmentException() {
        super("Employee and project must belong to the same department");
    }

    public DifferentDepartmentException(String message) {
        super(message);
    }
}
