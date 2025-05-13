package com.backend.backend.controller;

import com.backend.backend.model.Department;
import com.backend.backend.service.DepartmentService;
import com.backend.backend.dto.DepartmentRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping("/create")
    public ResponseEntity<Department> createDepartment(@Valid @RequestBody DepartmentRequestDTO departmentRequestDTO) {
        // El servicio espera un DepartmentRequestDTO
        Department newDepartment = departmentService.createDepartment(departmentRequestDTO);
        return new ResponseEntity<>(newDepartment, HttpStatus.CREATED);
    }

    @PostMapping("delete/{departmentId}") 
    public ResponseEntity<Department> logicallyDeleteDepartment(@PathVariable Integer departmentId) {
        // El servicio ahora lanzará excepciones si algo sale mal
        // Las excepciones serán manejadas por GlobalExceptionHandler
        Department updatedDepartment = departmentService.logicallyDeleteDepartment(departmentId);
        return ResponseEntity.ok(updatedDepartment);
    }
}