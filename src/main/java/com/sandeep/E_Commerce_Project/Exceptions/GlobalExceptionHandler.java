package com.sandeep.E_Commerce_Project.Exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest req
    ) {
        String msg = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(f -> f.getField() + ": " + f.getDefaultMessage())
                .toList()
                .toString();

        return ResponseEntity.badRequest().body(
                ApiResponse.error(msg, req.getRequestURI(), 400)
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<?>> handleBadRequest(
            IllegalArgumentException ex,
            HttpServletRequest req
    ) {
        return ResponseEntity.badRequest().body(
                ApiResponse.error(ex.getMessage(), req.getRequestURI(), 400)
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleOther(
            Exception ex,
            HttpServletRequest req
    ) {
        ex.printStackTrace();
        return ResponseEntity.status(500)
                .body(ApiResponse.error("Something went wrong", req.getRequestURI(), 500));
    }
}
