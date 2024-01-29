package com.hyperface.employeemanagementsystem.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
class RestExceptionHandler{
    def buildResponseEntity = { ApiError apiError ->
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseBody
    ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex) {

        ApiError apiError = new ApiError(UNAUTHORIZED,
                ex.getMessage());
        return buildResponseEntity(apiError);
    }
    @ExceptionHandler([ExpiredJwtException.class, SignatureException.class, MalformedJwtException.class])
    @ResponseBody
    ResponseEntity<Object> handleJWT(Exception ex) {

        ApiError apiError = new ApiError(UNAUTHORIZED,
                "Invalid token supplied");
        return buildResponseEntity(apiError);
    }
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {
        ex.printStackTrace()
        ApiError apiError = new ApiError(FORBIDDEN,
                "You don't have access to the requested resource.");
        return buildResponseEntity(apiError);
    }
    @ExceptionHandler(EntityNotFoundException.class)
    ResponseEntity<Object> handleEntityNotFound(
            Exception ex) {
        ApiError apiError = new ApiError(NOT_FOUND);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(DifferentDepartmentException.class)
    ResponseEntity<Object> handleDepartmentNotMatch(
            DifferentDepartmentException ex) {
        ApiError apiError = new ApiError(BAD_REQUEST);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<String> errorDetails = new ArrayList<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errorDetails.add(error.getField() + ": " + error.getDefaultMessage());
        }
        ApiError apiError=new ApiError(BAD_REQUEST,"Please enter valid information.",errorDetails);
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    ResponseEntity<Object> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        ApiError apiError=new ApiError(BAD_REQUEST,ex.getMessage());
        return buildResponseEntity(apiError);
    }
    @ExceptionHandler(RuntimeException.class)
     ResponseEntity<Object> handleRuntimeException(
            RuntimeException ex) {
        ApiError apiError = new ApiError(INTERNAL_SERVER_ERROR);
        ex.printStackTrace()
        apiError.setMessage("Sorry, We will fix it soon!");
        return buildResponseEntity(apiError);
    }
}
