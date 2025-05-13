package com.backend.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateDepartmentNameException extends RuntimeException {
    public DuplicateDepartmentNameException(String message) {
        super(message);
    }
}