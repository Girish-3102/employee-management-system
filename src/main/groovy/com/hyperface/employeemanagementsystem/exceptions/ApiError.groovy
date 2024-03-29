package com.hyperface.employeemanagementsystem.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;


@JsonInclude(JsonInclude.Include.NON_NULL)
class ApiError {
    String message;
    HttpStatus status;
    List<String> details;
    LocalDateTime timestamp;
    ApiError(){
        timestamp=LocalDateTime.now();
    }
    ApiError(HttpStatus status){
        this();
        this.status=status;
    }
    ApiError(HttpStatus status,String message){
        this();
        this.status=status;
        this.message=message;
    }
    ApiError(HttpStatus status,String message,List<String> details){
        this();
        this.status=status;
        this.message=message;
        this.details=details;
    }
}
