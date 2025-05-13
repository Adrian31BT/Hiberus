package com.backend.backend.repository;

import com.backend.backend.model.Department;
import com.backend.backend.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate; 
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    boolean existsByDepartmentAndEmployeeStatus(Department department, String employeeStatus);
    Long countByInitDateBetween(LocalDate startDate, LocalDate endDate);
}