package com.backend.backend.dto;

import java.math.BigDecimal;

public class EmployeeSalaryDTO {
    private String employeeName;
    private String employeeLastName;
    private BigDecimal salary;

    public EmployeeSalaryDTO(String employeeName, String employeeLastName, BigDecimal salary) {
        this.employeeName = employeeName;
        this.employeeLastName = employeeLastName;
        this.salary = salary;
    }

    // Getters
    public String getEmployeeName() {
        return employeeName;
    }

    public String getEmployeeLastName() {
        return employeeLastName;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    // Setters 
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public void setEmployeeLastName(String employeeLastName) {
        this.employeeLastName = employeeLastName;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }
}