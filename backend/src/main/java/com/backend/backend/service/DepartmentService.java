package com.backend.backend.service;

import com.backend.backend.model.Department;
import com.backend.backend.repository.DepartmentRepository;
import com.backend.backend.dto.DepartmentRequestDTO;
import com.backend.backend.exception.DepartmentAlreadyInactiveException;
import com.backend.backend.exception.DepartmentNotFoundException;
import com.backend.backend.exception.DuplicateDepartmentNameException;
import com.backend.backend.exception.DepartmentHasActiveEmployeesException; 
import com.backend.backend.repository.EmployeeRepository; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository; 

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository) { 
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository; 
    }

    @Transactional
    public Department createDepartment(DepartmentRequestDTO departmentRequestDTO) {
        // Validación que no permite nombres de departamento duplicados
        departmentRepository.findByDepartmentNameIgnoreCase(departmentRequestDTO.getDepartmentName())
            .ifPresent(d -> {
                throw new DuplicateDepartmentNameException("Ya existe un departamento con el nombre: " + departmentRequestDTO.getDepartmentName());
            });

        Department department = new Department();
        department.setDepartmentName(departmentRequestDTO.getDepartmentName());
        department.setDepartmentStatus(departmentRequestDTO.getDepartmentStatus());

        return departmentRepository.save(department);
    }

    @Transactional
    public Department logicallyDeleteDepartment(Integer departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new DepartmentNotFoundException("Departamento no encontrado con ID: " + departmentId));

        // Validación para no inactivar un departamento que ya está inactivo
        if ("I".equals(department.getDepartmentStatus())) {
            throw new DepartmentAlreadyInactiveException("El departamento con ID: " + departmentId + " ya se encuentra inactivo.");
        }

        // Validación que no permite inactivar si hay empleados activos dentro del departamento
        boolean hasActiveEmployees = employeeRepository.existsByDepartmentAndEmployeeStatus(department, "A");
        if (hasActiveEmployees) {
            throw new DepartmentHasActiveEmployeesException("No se puede inactivar el departamento con ID: " + departmentId + " porque tiene empleados activos.");
        } 

        department.setDepartmentStatus("I"); 
        return departmentRepository.save(department);
    }
}