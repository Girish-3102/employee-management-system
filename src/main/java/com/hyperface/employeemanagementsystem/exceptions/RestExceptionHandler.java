package com.hyperface.employeemanagementsystem.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class RestExceptionHandler{
    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
    @ExceptionHandler({BadCredentialsException.class})
    @ResponseBody
    public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex) {

        ApiError apiError = new ApiError(UNAUTHORIZED,
                ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
    }
    @ExceptionHandler({ExpiredJwtException.class, SignatureException.class})
    @ResponseBody
    public ResponseEntity<Object> handleJWT(Exception ex) {

        ApiError apiError = new ApiError(UNAUTHORIZED,
                "Invalid token supplied");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
    }
    @ExceptionHandler({AccessDeniedException.class})
    @ResponseBody
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {

        ApiError apiError = new ApiError(FORBIDDEN,
                "You don't have access to the requested resource.");
        return ResponseEntity.status(FORBIDDEN).body(apiError);
    }
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFound(
            EntityNotFoundException ex) {
        ApiError apiError = new ApiError(NOT_FOUND);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(DifferentDepartmentException.class)
    public ResponseEntity<Object> handleDepartmentNotMatch(
            DifferentDepartmentException ex) {
        ApiError apiError = new ApiError(BAD_REQUEST);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<String> errorDetails = new ArrayList<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errorDetails.add(error.getField() + ": " + error.getDefaultMessage());
        }
        ApiError apiError=new ApiError(BAD_REQUEST,"Please enter valid information.",errorDetails);
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Object> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        ApiError apiError=new ApiError(BAD_REQUEST,ex.getMessage());
        return buildResponseEntity(apiError);
    }
    @ExceptionHandler(RuntimeException.class)
     public ResponseEntity<Object> handleRuntimeException(
            RuntimeException ex) {
        ApiError apiError = new ApiError(INTERNAL_SERVER_ERROR);
        apiError.setMessage("Sorry, We will fix it soon!");
        return buildResponseEntity(apiError);
    }

}
