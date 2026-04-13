//package com.pm.patientservice1.exception;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//import java.util.HashMap;
//import java.util.Map;
////these ExceptionHandlers catches the exception thrown by the respective class given below return the particular specific exception alone hiding the unnecessary data to the client like revealing the class name like requestDTO.field is null instead of like that showing Field is mandatory or cannot be null
//@ControllerAdvice
//public class GlobalExceptionHandler {
//    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<Map<String,String>>  handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
//        Map<String,String> errors = new HashMap<>();
//        ex.getBindingResult().getFieldErrors().forEach((error)->{
//            errors.put(error.getField(),error.getDefaultMessage());
//
//        });
//        return  ResponseEntity.badRequest().body(errors);
//
//    }
//    @ExceptionHandler(EmailAlreadyExistsException.class)
//    public ResponseEntity<Map<String,String>> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex){
//        log.warn(ex.getMessage());
//        Map<String,String> errors = new HashMap<>();
//        errors.put("message",ex.getMessage());
//        return  ResponseEntity.badRequest().body(errors);
//
//    }
//    @ExceptionHandler(PatientNotFoundException.class)
//    public ResponseEntity<Map<String,String>> handlePatientNotFoundException(PatientNotFoundException ex){
//        Map<String,String> errors = new HashMap<>();
//        errors.put("message","Patient Not Found");
//        return  ResponseEntity.badRequest().body(errors);
//    }
//
//
//}
package com.pm.patientservice1.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 🔹 Validation Errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {

        Map<String, String> fieldErrors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                fieldErrors.put(error.getField(), error.getDefaultMessage())
        );

        return buildResponse("Validation Failed", HttpStatus.BAD_REQUEST, fieldErrors);
    }

    // 🔹 Email Exists (Business Exception)
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleEmailExists(EmailAlreadyExistsException ex) {

        log.warn(ex.getMessage());
        return buildResponse(ex.getMessage(), HttpStatus.CONFLICT, null);
    }

    // 🔹 Patient Not Found
    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(PatientNotFoundException ex) {

        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND, null);
    }

    // 🔹 Generic Exception (Fallback)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex) {

        log.error("Unexpected error: ", ex);
        return buildResponse("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR, null);
    }

    // 🔧 Common Response Builder
    private ResponseEntity<Map<String, Object>> buildResponse(
            String message,
            HttpStatus status,
            Object details
    ) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("message", message);

        if (details != null) {
            body.put("details", details);
        }

        return new ResponseEntity<>(body, status);
    }
}
