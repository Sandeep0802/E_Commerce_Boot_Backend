package com.sandeep.E_Commerce_Project.Exceptions;

import lombok.Data;

import java.time.Instant;

@Data
public class ApiResponse<T> {

    private Instant timestamp = Instant.now();
    private boolean success;
    private String message;
    private T data;     // optional (null for errors)
    private String path; // URL path
    private Integer status;

    // SUCCESS RESPONSE
    public static <T> ApiResponse<T> success(String message, T data, String path, int status) {
        ApiResponse<T> res = new ApiResponse<>();
        res.success = true;
        res.message = message;
        res.data = data;
        res.path = path;
        res.status = status;
        return res;
    }

    // ERROR RESPONSE
    public static <T> ApiResponse<T> error(String message, String path, int status) {
        ApiResponse<T> res = new ApiResponse<>();
        res.success = false;
        res.message = message;
        res.data = null;
        res.path = path;
        res.status = status;
        return res;
    }
}
