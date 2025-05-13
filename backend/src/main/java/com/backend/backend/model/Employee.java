package com.backend.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Employee_id")
    private Integer employeeId;

    // Columna FK en la tabla Employee
    @ManyToOne
    @JoinColumn(name = "Department_id") 
    private Department department;

    @Column(name = "Employee_name", nullable = false)
    private String employeeName;

    @Column(name = "Employee_last_name", nullable = false)
    private String employeeLastName;

    @Column(name = "Age")
    private Integer age;

    @Column(name = "Salary")
    private BigDecimal salary;

    @Column(name = "Init_date", nullable = false)
    private LocalDate initDate;

    @Column(name = "End_date")
    private LocalDate endDate;

    @Column(name = "Employee_status", nullable = false, length = 1)
    private String employeeStatus;

    // Constructores
    public Employee() {
    }

    public Employee(Department department, String employeeName, String employeeLastName, Integer age, BigDecimal salary, LocalDate initDate, LocalDate endDate, String employeeStatus) {
        this.department = department;
        this.employeeName = employeeName;
        this.employeeLastName = employeeLastName;
        this.age = age;
        this.salary = salary;
        this.initDate = initDate;
        this.endDate = endDate;
        this.employeeStatus = employeeStatus;
    }

    // Getters y Setters
    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
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
}