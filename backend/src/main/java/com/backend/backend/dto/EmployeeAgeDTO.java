package com.backend.backend.dto;

public class EmployeeAgeDTO {
    private String employeeName;
    private String employeeLastName;
    private Integer age;

    public EmployeeAgeDTO(String employeeName, String employeeLastName, Integer age) {
        this.employeeName = employeeName;
        this.employeeLastName = employeeLastName;
        this.age = age;
    }

    // Getters
    public String getEmployeeName() {
        return employeeName;
    }

    public String getEmployeeLastName() {
        return employeeLastName;
    }

    public Integer getAge() {
        return age;
    }

    // Setters 
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public void setEmployeeLastName(String employeeLastName) {
        this.employeeLastName = employeeLastName;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}