package com.backend.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EmployeeResponseDTO {
    private Integer employeeId;
    private String employeeName;
    private String employeeLastName;
    private Integer age;
    private BigDecimal salary;
    private LocalDate initDate;
    private LocalDate endDate;
    private String employeeStatus;
    private DepartmentInfoDTO department;
    
    // DTO anidado para información del departamento
    public static class DepartmentInfoDTO {
        private Integer departmentId;
        private String departmentName;
        
        public DepartmentInfoDTO(Integer departmentId, String departmentName) {
            this.departmentId = departmentId;
            this.departmentName = departmentName;
        }
        
        // Getters y setters
        public Integer getDepartmentId() {
            return departmentId;
        }
        
        public void setDepartmentId(Integer departmentId) {
            this.departmentId = departmentId;
        }
        
        public String getDepartmentName() {
            return departmentName;
        }
        
        public void setDepartmentName(String departmentName) {
            this.departmentName = departmentName;
        }
    }

    // Constructor
    public EmployeeResponseDTO(Integer employeeId, String employeeName, String employeeLastName, 
                           Integer age, BigDecimal salary, LocalDate initDate, LocalDate endDate, 
                           String employeeStatus, DepartmentInfoDTO department) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeeLastName = employeeLastName;
        this.age = age;
        this.salary = salary;
        this.initDate = initDate;
        this.endDate = endDate;
        this.employeeStatus = employeeStatus;
        this.department = department;
    }

    // Getters y setters
    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeLastName() {
        return employeeLastName;
    }

    public void setEmployeeLastName(String employeeLastName) {
        this.employeeLastName = employeeLastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public LocalDate getInitDate() {
        return initDate;
    }

    public void setInitDate(LocalDate initDate) {
        this.initDate = initDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getEmployeeStatus() {
        return employeeStatus;
    }

    public void setEmployeeStatus(String employeeStatus) {
        this.employeeStatus = employeeStatus;
    }

    public DepartmentInfoDTO getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentInfoDTO department) {
        this.department = department;
    }
}