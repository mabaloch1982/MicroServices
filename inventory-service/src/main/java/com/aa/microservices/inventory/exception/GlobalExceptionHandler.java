package com.aa.microservices.inventory.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)//handling general exception if not handled in specific exception for example handleResourceNotfoundException
    public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex, WebRequest webRequest) {
        ErrorDetails details = new ErrorDetails(LocalDateTime.now(),
                ex.getMessage(), webRequest.getDescription(true),
                HttpStatus.INTERNAL_SERVER_ERROR.toString(), Arrays.toString(ex.getStackTrace()));//TBD print stack later
        log.error("Exception: ",ex);
        return new ResponseEntity<ErrorDetails>(details,HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(ResourceNotFoundException.class) //can add multiple classes in parameter
    public final ResponseEntity<ErrorDetails> handleResourNotFoundExceptions(ResourceNotFoundException ex,WebRequest webRequest) {
        ErrorDetails details = new ErrorDetails(LocalDateTime.now(),
                ex.getMessage(), webRequest.getDescription(true),
                HttpStatus.NOT_FOUND.toString(), ex.getStackTrace().toString());
      log.error("Exception: ",ex);
        return new ResponseEntity<ErrorDetails>(details,HttpStatus.NOT_FOUND);

    }

}
