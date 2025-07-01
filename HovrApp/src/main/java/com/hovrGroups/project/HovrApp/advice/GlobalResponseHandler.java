package com.hovrGroups.project.HovrApp.advice;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class GlobalResponseHandler {

    public <T> ResponseEntity<ApiResponse<T>> success(String message, T data) {
        return ResponseEntity.ok(new ApiResponse<>(true, message, data));
    }

    public ResponseEntity<ApiResponse<Object>> error(String message) {
        return ResponseEntity.badRequest().body(new ApiResponse<>(false, message, null));
    }
}