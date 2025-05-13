package com.backend.backend.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class EmployeeRequestDTO {

    @NotBlank(message = "El nombre del empleado no puede estar vacío")
    @Size(min = 3, max = 100, message = "El nombre del empleado debe tener entre 3 y 100 caracteres")
    private String employeeName;

    @NotBlank(message = "El apellido del empleado no puede estar vacío")
    @Size(min = 3, max = 100, message = "El apellido del empleado debe tener entre 3 y 100 caracteres")
    private String employeeLastName;

    @NotNull(message = "La edad no puede ser nula")
    @Min(value = 18, message = "La edad mínima debe ser 18 años")
    @Max(value = 70, message = "La edad máxima debe ser 70 años")
    private Integer age;

    @DecimalMin(value = "0.0", inclusive = false, message = "El salario debe ser mayor que 0")

    private BigDecimal salary;

    @NotNull(message = "La fecha de inicio no puede ser nula")

    private LocalDate initDate;

    private LocalDate endDate;

    @NotBlank(message = "El estado del empleado no puede estar vacío")
    @Pattern(regexp = "[AI]", message = "El estado del empleado debe ser 'A' (Activo) o 'I' (Inactivo)")
    private String employeeStatus;

    // Getters y Setters
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