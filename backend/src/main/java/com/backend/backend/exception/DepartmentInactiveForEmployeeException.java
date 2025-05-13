package com.backend.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DepartmentInactiveForEmployeeException extends RuntimeException {
    public DepartmentInactiveForEmployeeException(String message) {
        super(message);
    }
}