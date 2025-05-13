package com.backend.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class DepartmentRequestDTO {

    @NotBlank(message = "El nombre del departamento no puede estar vacío")
    @Size(min = 3, max = 100, message = "El nombre del departamento debe tener entre 3 y 100 caracteres")
    private String departmentName;

    @NotBlank(message = "El estado del departamento no puede estar vacío")
    @Pattern(regexp = "[AI]", message = "El estado del departamento debe ser 'A' (Activo) o 'I' (Inactivo)")
    private String departmentStatus;

    // Getters y Setters
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentStatus() {
        return departmentStatus;
    }

    public void setDepartmentStatus(String departmentStatus) {
        this.departmentStatus = departmentStatus;
    }
}