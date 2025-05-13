package com.backend.backend.controller;

import com.backend.backend.dto.EmployeeAgeDTO;
import com.backend.backend.dto.EmployeeRequestDTO;
import com.backend.backend.dto.EmployeeResponseDTO;
import com.backend.backend.dto.EmployeeSalaryDTO;
import com.backend.backend.model.Employee;
import com.backend.backend.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/create/{departmentId}")
    public ResponseEntity<Employee> createEmployee(
            @PathVariable Integer departmentId,
            @Valid @RequestBody EmployeeRequestDTO employeeRequestDTO) {
        Employee newEmployee = employeeService.createEmployee(departmentId, employeeRequestDTO);
        return new ResponseEntity<>(newEmployee, HttpStatus.CREATED);
    }

    @PostMapping("/delete/{employeeId}")
    public ResponseEntity<Employee> logicallyDeleteEmployee(@PathVariable Integer employeeId) {
        Employee updatedEmployee = employeeService.logicallyDeleteEmployee(employeeId);
        return ResponseEntity.ok(updatedEmployee);
    }

    @GetMapping("/highestSalary")
    public ResponseEntity<EmployeeSalaryDTO> getHighestSalaryEmployee() {
        EmployeeSalaryDTO employeeSalaryDTO = employeeService.getEmployeeWithHighestSalary();
        return ResponseEntity.ok(employeeSalaryDTO);
    }

    @GetMapping("/lowerAge")
    public ResponseEntity<EmployeeAgeDTO> getLowestAgeEmployee() {
        EmployeeAgeDTO employeeAgeDTO = employeeService.getEmployeeWithLowestAge();
        return ResponseEntity.ok(employeeAgeDTO);
    }

    @GetMapping("/countLastMonth")
    public ResponseEntity<Long> countEmployeesHiredLastMonth() {
        Long count = employeeService.countEmployeesHiredLastMonth();
        return ResponseEntity.ok(count);
    }

    @RequestMapping("/all")
    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees() {
        List<EmployeeResponseDTO> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }
}