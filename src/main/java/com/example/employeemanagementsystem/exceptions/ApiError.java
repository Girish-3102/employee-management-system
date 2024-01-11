package com.example.employeemanagementsystem.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Data
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {
    private String message;
    private HttpStatus status;
    private List<String> details;
    private LocalDateTime timestamp;
    public ApiError(){
        timestamp=LocalDateTime.now();
    }
    public ApiError(HttpStatus status){
        this();
        this.status=status;
    }
    public ApiError(HttpStatus status,String message){
        this();
        this.status=status;
        this.message=message;
    }
    public ApiError(HttpStatus status,String message,List<String> details){
        this();
        this.status=status;
        this.message=message;
        this.details=details;
    }
}
