package com.backend.backend.repository;

import com.backend.backend.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional; 

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    // Método para buscar un departamento por su nombre, ignorando mayúsculas/minúsculas.
    Optional<Department> findByDepartmentNameIgnoreCase(String departmentName);
}