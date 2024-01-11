package com.example.employeemanagementsystem.exceptions;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Data
@Getter
@Setter
public class ApiError {
    private String message;
    private HttpStatus status;
    public ApiError(){}
    public ApiError(HttpStatus status){
        this.status=status;
    }
    public ApiError(HttpStatus status,String message){
        this.status=status;
        this.message=message;
    }
}
