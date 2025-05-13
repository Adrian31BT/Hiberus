package com.backend.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DepartmentForEmployeeNotFoundException extends RuntimeException {
    public DepartmentForEmployeeNotFoundException(String message) {
        super(message);
    }
}