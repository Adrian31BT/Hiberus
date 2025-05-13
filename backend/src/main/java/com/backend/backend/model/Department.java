package com.backend.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;

@Entity
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "Department_id")
    private Integer departmentId;

    @Column(name = "Department_name", nullable = false)
    private String departmentName;

    @Column(name = "Department_status", nullable = false, length = 1)
    private String departmentStatus;

    // Constructores
    public Department() {
    }

    public Department(String departmentName, String departmentStatus) {
        this.departmentName = departmentName;
        this.departmentStatus = departmentStatus;
    }

    // Getters y Setters
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

    public String getDepartmentStatus() {
        return departmentStatus;
    }

    public void setDepartmentStatus(String departmentStatus) {
        this.departmentStatus = departmentStatus;
    }
}