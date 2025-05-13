package com.backend.backend.service;

import com.backend.backend.dto.EmployeeAgeDTO;
import com.backend.backend.dto.EmployeeRequestDTO;
import com.backend.backend.dto.EmployeeResponseDTO;
import com.backend.backend.dto.EmployeeSalaryDTO;
import com.backend.backend.exception.DepartmentForEmployeeNotFoundException;
import com.backend.backend.exception.DepartmentInactiveForEmployeeException;
import com.backend.backend.exception.EmployeeAlreadyInactiveException;
import com.backend.backend.exception.EmployeeNotFoundException;
import com.backend.backend.model.Department;
import com.backend.backend.model.Employee;
import com.backend.backend.repository.DepartmentRepository;
import com.backend.backend.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.time.LocalDate;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    @Transactional
    public Employee createEmployee(Integer departmentId, EmployeeRequestDTO employeeRequestDTO) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new DepartmentForEmployeeNotFoundException(
                        "Departamento con ID: " + departmentId + " no encontrado para asignar al empleado."));

        if (!"A".equals(department.getDepartmentStatus())) {
            throw new DepartmentInactiveForEmployeeException("El departamento con ID: " + departmentId
                    + " está inactivo y no se pueden asignar nuevos empleados.");
        }

        if (employeeRequestDTO.getEndDate() != null && employeeRequestDTO.getInitDate() != null &&
                employeeRequestDTO.getEndDate().isBefore(employeeRequestDTO.getInitDate())) {
            throw new IllegalArgumentException(
                    "La fecha de fin del empleado no puede ser anterior a la fecha de inicio.");
        }

        Employee employee = new Employee();
        employee.setDepartment(department);
        employee.setEmployeeName(employeeRequestDTO.getEmployeeName());
        employee.setEmployeeLastName(employeeRequestDTO.getEmployeeLastName());
        employee.setAge(employeeRequestDTO.getAge());
        employee.setSalary(employeeRequestDTO.getSalary());
        employee.setInitDate(employeeRequestDTO.getInitDate());
        employee.setEndDate(employeeRequestDTO.getEndDate());
        employee.setEmployeeStatus(employeeRequestDTO.getEmployeeStatus());

        return employeeRepository.save(employee);
    }

    @Transactional
    public Employee logicallyDeleteEmployee(Integer employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Empleado no encontrado con ID: " + employeeId));

        if ("I".equals(employee.getEmployeeStatus())) {
            throw new EmployeeAlreadyInactiveException(
                    "El empleado con ID: " + employeeId + " ya se encuentra inactivo.");
        }

        employee.setEmployeeStatus("I");
        return employeeRepository.save(employee);
    }

    @Transactional(readOnly = true)
    public EmployeeSalaryDTO getEmployeeWithHighestSalary() {
        List<Employee> allEmployees = employeeRepository.findAll();

        if (allEmployees.isEmpty()) {
            throw new EmployeeNotFoundException("No se encontraron empleados para determinar el salario más alto.");
        }

        Optional<Employee> employeeWithHighestSalaryOpt = allEmployees.stream()
                .filter(e -> e.getSalary() != null)
                .max(Comparator.comparing(Employee::getSalary));

        if (employeeWithHighestSalaryOpt.isEmpty()) {
            throw new EmployeeNotFoundException(
                    "No se encontraron empleados con salario válido para determinar el más alto.");
        }

        Employee employee = employeeWithHighestSalaryOpt.get();
        return new EmployeeSalaryDTO(employee.getEmployeeName(), employee.getEmployeeLastName(), employee.getSalary());
    }

    @Transactional(readOnly = true)
    public EmployeeAgeDTO getEmployeeWithLowestAge() {
        List<Employee> allEmployees = employeeRepository.findAll();

        if (allEmployees.isEmpty()) {
            throw new EmployeeNotFoundException("No se encontraron empleados para determinar el más joven.");
        }

        Optional<Employee> employeeWithLowestAgeOpt = allEmployees.stream()
                .filter(e -> e.getAge() != null)
                .min(Comparator.comparing(Employee::getAge));

        if (employeeWithLowestAgeOpt.isEmpty()) {
            throw new EmployeeNotFoundException(
                    "No se encontraron empleados con edad válida para determinar el más joven.");
        }
        Employee employee = employeeWithLowestAgeOpt.get();
        return new EmployeeAgeDTO(employee.getEmployeeName(), employee.getEmployeeLastName(), employee.getAge());
    }

    @Transactional(readOnly = true)
    public Long countEmployeesHiredLastMonth() {
        LocalDate today = LocalDate.now();
        LocalDate oneMonthAgo = today.minusMonths(1);
        LocalDate startDate = oneMonthAgo;
        LocalDate endDate = today;
        return employeeRepository.countByInitDateBetween(startDate, endDate);
    }

    @Transactional(readOnly = true)
    public List<EmployeeResponseDTO> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();

        if (employees.isEmpty()) {
            throw new EmployeeNotFoundException("No se encontraron empleados en la base de datos");
        }

        return employees.stream()
                .map(this::mapToEmployeeResponseDTO)
                .collect(Collectors.toList());
    }

    private EmployeeResponseDTO mapToEmployeeResponseDTO(Employee employee) {
        EmployeeResponseDTO.DepartmentInfoDTO departmentInfo = null;

        if (employee.getDepartment() != null) {
            departmentInfo = new EmployeeResponseDTO.DepartmentInfoDTO(
                    employee.getDepartment().getDepartmentId(),
                    employee.getDepartment().getDepartmentName());
        }

        return new EmployeeResponseDTO(
                employee.getEmployeeId(),
                employee.getEmployeeName(),
                employee.getEmployeeLastName(),
                employee.getAge(),
                employee.getSalary(),
                employee.getInitDate(),
                employee.getEndDate(),
                employee.getEmployeeStatus(),
                departmentInfo);
    }
}