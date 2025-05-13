package com.backend.backend.controller;

import com.backend.backend.dto.EmployeeAgeDTO;
import com.backend.backend.dto.EmployeeRequestDTO;
import com.backend.backend.dto.EmployeeResponseDTO;
import com.backend.backend.dto.EmployeeSalaryDTO;
import com.backend.backend.exception.*;
import com.backend.backend.model.Department;
import com.backend.backend.model.Employee;
import com.backend.backend.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private EmployeeService employeeService;

        @Autowired
        private ObjectMapper objectMapper;

        private EmployeeRequestDTO employeeRequestDTO;
        private Employee employee;
        private Department department;
        private Integer departmentId = 1;
        private Integer employeeId = 1;

        @BeforeEach
        void setUp() {

                department = new Department();
                department.setDepartmentId(departmentId);
                department.setDepartmentName("IT");
                department.setDepartmentStatus("A");

                employeeRequestDTO = new EmployeeRequestDTO();
                employeeRequestDTO.setEmployeeName("John");
                employeeRequestDTO.setEmployeeLastName("Doe");
                employeeRequestDTO.setAge(30);
                employeeRequestDTO.setSalary(new BigDecimal("50000.00"));
                employeeRequestDTO.setInitDate(LocalDate.of(2023, 1, 15));
                employeeRequestDTO.setEmployeeStatus("A");

                employee = new Employee();
                employee.setEmployeeId(employeeId);
                employee.setDepartment(department);
                employee.setEmployeeName("John");
                employee.setEmployeeLastName("Doe");
                employee.setAge(30);
                employee.setSalary(new BigDecimal("50000.00"));
                employee.setInitDate(LocalDate.of(2023, 1, 15));
                employee.setEmployeeStatus("A");
        }

        // Pruebas para POST /api/employee/create/{departmentId}
        @Test
        void createEmployee_whenValidInput_shouldReturnCreatedEmployee() throws Exception {
                when(employeeService.createEmployee(eq(departmentId), any(EmployeeRequestDTO.class)))
                                .thenReturn(employee);

                ResultActions resultActions = mockMvc.perform(post("/api/employee/create/{departmentId}", departmentId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(employeeRequestDTO)));

                resultActions.andExpect(status().isCreated())
                                .andExpect(jsonPath("$.employeeId", is(employee.getEmployeeId())))
                                .andExpect(jsonPath("$.employeeName", is(employee.getEmployeeName())));
        }

        @Test
        void createEmployee_whenDepartmentNotFound_shouldReturnNotFound() throws Exception {
                when(employeeService.createEmployee(eq(departmentId), any(EmployeeRequestDTO.class)))
                                .thenThrow(new DepartmentForEmployeeNotFoundException("Departamento no encontrado"));

                ResultActions resultActions = mockMvc.perform(post("/api/employee/create/{departmentId}", departmentId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(employeeRequestDTO)));

                resultActions.andExpect(status().isNotFound());
        }

        @Test
        void createEmployee_whenDepartmentInactive_shouldReturnBadRequest() throws Exception {
                when(employeeService.createEmployee(eq(departmentId), any(EmployeeRequestDTO.class)))
                                .thenThrow(new DepartmentInactiveForEmployeeException("Departamento inactivo"));

                ResultActions resultActions = mockMvc.perform(post("/api/employee/create/{departmentId}", departmentId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(employeeRequestDTO)));

                resultActions.andExpect(status().isBadRequest());
        }

        @Test
        void createEmployee_whenInvalidDateLogic_shouldReturnBadRequest() throws Exception {
                employeeRequestDTO.setInitDate(LocalDate.of(2024, 1, 1));
                employeeRequestDTO.setEndDate(LocalDate.of(2023, 1, 1)); // EndDate antes de InitDate

                when(employeeService.createEmployee(eq(departmentId), any(EmployeeRequestDTO.class)))
                                .thenThrow(new IllegalArgumentException(
                                                "La fecha de fin del empleado no puede ser anterior a la fecha de inicio."));

                ResultActions resultActions = mockMvc.perform(post("/api/employee/create/{departmentId}", departmentId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(employeeRequestDTO)));

                resultActions.andExpect(status().isBadRequest());
        }

        // Pruebas para POST /api/employee/delete/{employeeId}
        @Test
        void logicallyDeleteEmployee_whenEmployeeExists_shouldReturnOk() throws Exception {
                Employee inactiveEmployee = new Employee();
                inactiveEmployee.setEmployeeId(employeeId);
                inactiveEmployee.setEmployeeStatus("I");
                when(employeeService.logicallyDeleteEmployee(eq(employeeId))).thenReturn(inactiveEmployee);

                ResultActions resultActions = mockMvc.perform(post("/api/employee/delete/{employeeId}", employeeId));

                resultActions.andExpect(status().isOk())
                                .andExpect(jsonPath("$.employeeStatus", is("I")));
        }

        @Test
        void logicallyDeleteEmployee_whenEmployeeNotFound_shouldReturnNotFound() throws Exception {
                when(employeeService.logicallyDeleteEmployee(eq(employeeId)))
                                .thenThrow(new EmployeeNotFoundException("Empleado no encontrado"));

                ResultActions resultActions = mockMvc.perform(post("/api/employee/delete/{employeeId}", employeeId));

                resultActions.andExpect(status().isNotFound());
        }

        @Test
        void logicallyDeleteEmployee_whenEmployeeAlreadyInactive_shouldReturnBadRequest() throws Exception {
                when(employeeService.logicallyDeleteEmployee(eq(employeeId)))
                                .thenThrow(new EmployeeAlreadyInactiveException("Empleado ya inactivo"));

                ResultActions resultActions = mockMvc.perform(post("/api/employee/delete/{employeeId}", employeeId));

                resultActions.andExpect(status().isBadRequest());
        }

        // Pruebas para GET /api/employee/highestSalary
        @Test
        void getHighestSalaryEmployee_whenEmployeesExist_shouldReturnEmployeeSalaryDTO() throws Exception {
                EmployeeSalaryDTO salaryDTO = new EmployeeSalaryDTO("John", "Doe", new BigDecimal("90000.00"));
                when(employeeService.getEmployeeWithHighestSalary()).thenReturn(salaryDTO);

                ResultActions resultActions = mockMvc.perform(get("/api/employee/highestSalary"));

                resultActions.andExpect(status().isOk())
                                .andExpect(jsonPath("$.employeeName", is("John")))
                                .andExpect(jsonPath("$.salary", is(90000.00)));
        }

        @Test
        void getHighestSalaryEmployee_whenNoEmployees_shouldReturnNotFound() throws Exception {
                when(employeeService.getEmployeeWithHighestSalary())
                                .thenThrow(new EmployeeNotFoundException("No se encontraron empleados"));

                ResultActions resultActions = mockMvc.perform(get("/api/employee/highestSalary"));

                resultActions.andExpect(status().isNotFound());
        }

        // Pruebas para GET /api/employee/lowerAge
        @Test
        void getLowestAgeEmployee_whenEmployeesExist_shouldReturnEmployeeAgeDTO() throws Exception {
                EmployeeAgeDTO ageDTO = new EmployeeAgeDTO("Jane", "Doe", 22);
                when(employeeService.getEmployeeWithLowestAge()).thenReturn(ageDTO);

                ResultActions resultActions = mockMvc.perform(get("/api/employee/lowerAge"));

                resultActions.andExpect(status().isOk())
                                .andExpect(jsonPath("$.employeeName", is("Jane")))
                                .andExpect(jsonPath("$.age", is(22)));
        }

        @Test
        void getLowestAgeEmployee_whenNoEmployees_shouldReturnNotFound() throws Exception {
                when(employeeService.getEmployeeWithLowestAge())
                                .thenThrow(new EmployeeNotFoundException("No se encontraron empleados"));

                ResultActions resultActions = mockMvc.perform(get("/api/employee/lowerAge"));

                resultActions.andExpect(status().isNotFound());
        }

        // Pruebas para GET /api/employee/countLastMonth
        @Test
        void countEmployeesHiredLastMonth_shouldReturnCount() throws Exception {
                Long count = 5L;
                when(employeeService.countEmployeesHiredLastMonth()).thenReturn(count);

                ResultActions resultActions = mockMvc.perform(get("/api/employee/countLastMonth"));

                resultActions.andExpect(status().isOk())
                                .andExpect(content().string(count.toString()));
        }

        // Pruebas para GET /api/employee/all
        @Test
        void getAllEmployees_whenEmployeesExist_shouldReturnListOfEmployees() throws Exception {

                EmployeeResponseDTO.DepartmentInfoDTO deptInfo = new EmployeeResponseDTO.DepartmentInfoDTO(1, "IT");
                EmployeeResponseDTO employee1 = new EmployeeResponseDTO(1, "John", "Doe", 30,
                                new BigDecimal("50000.00"), LocalDate.of(2023, 1, 15), null, "A", deptInfo);
                EmployeeResponseDTO employee2 = new EmployeeResponseDTO(2, "Jane", "Smith", 25,
                                new BigDecimal("60000.00"), LocalDate.of(2023, 2, 20), null, "A", deptInfo);

                List<EmployeeResponseDTO> employees = Arrays.asList(employee1, employee2);

                when(employeeService.getAllEmployees()).thenReturn(employees);

                ResultActions resultActions = mockMvc.perform(get("/api/employee/all"));

                resultActions.andExpect(status().isOk())
                                .andExpect(jsonPath("$", hasSize(2)))
                                .andExpect(jsonPath("$[0].employeeId", is(1)))
                                .andExpect(jsonPath("$[0].employeeName", is("John")))
                                .andExpect(jsonPath("$[1].employeeId", is(2)))
                                .andExpect(jsonPath("$[1].employeeName", is("Jane")));
        }

        @Test
        void getAllEmployees_whenNoEmployees_shouldReturnNotFound() throws Exception {

                when(employeeService.getAllEmployees())
                                .thenThrow(new EmployeeNotFoundException("No se encontraron empleados"));
                ResultActions resultActions = mockMvc.perform(get("/api/employee/all"));

                resultActions.andExpect(status().isNotFound());
        }
}