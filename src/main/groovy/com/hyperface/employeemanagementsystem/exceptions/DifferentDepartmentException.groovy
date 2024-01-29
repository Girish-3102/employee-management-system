package com.hyperface.employeemanagementsystem.exceptions;

class DifferentDepartmentException extends RuntimeException {

    DifferentDepartmentException() {
        super("Employee and project must belong to the same department");
    }

    DifferentDepartmentException(String message) {
        super(message);
    }
}
