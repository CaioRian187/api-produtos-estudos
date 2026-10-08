package com.api.produtos.config.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandle {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> methodArgumentNotValidExceptionHandle(MethodArgumentNotValidException ex){
        Map<String, Object> response = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(
                error -> {
                    response.put(error.getField(), error.getDefaultMessage());
        });
        return ResponseEntity.status(ex.getStatusCode()).body(response);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, String>> responseStatusExceptionHandle(ResponseStatusException ex){
        Map<String, String> response = new HashMap<>();
        response.put(ex.getStatusCode().toString(), ex.getReason());
        return ResponseEntity.status(ex.getStatusCode()).body(response);
    }
}
