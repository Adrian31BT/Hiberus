package com.backend.backend.controller;

import com.backend.backend.dto.DepartmentRequestDTO;
import com.backend.backend.exception.DepartmentAlreadyInactiveException;
import com.backend.backend.exception.DepartmentHasActiveEmployeesException;
import com.backend.backend.exception.DepartmentNotFoundException;
import com.backend.backend.exception.DuplicateDepartmentNameException;
import com.backend.backend.model.Department;
import com.backend.backend.service.DepartmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;

@WebMvcTest(DepartmentController.class) 
public class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc; // Simula peticiones HTTP

    // Mock de DepartmentService y lo inyecta en el contexto de la aplicación para esta prueba
    @MockBean 
    private DepartmentService departmentService;

    @Autowired
    private ObjectMapper objectMapper;

    private DepartmentRequestDTO departmentRequestDTO;
    private Department department;

    @BeforeEach
    void setUp() {
        // Datos comunes para las pruebas
        departmentRequestDTO = new DepartmentRequestDTO();
        departmentRequestDTO.setDepartmentName("Riesgo");
        departmentRequestDTO.setDepartmentStatus("A");

        department = new Department();
        department.setDepartmentId(1);
        department.setDepartmentName("Riesgo");
        department.setDepartmentStatus("A");
    }

    // Pruebas para el endpoint POST /api/department/create
    @Test
    void createDepartment_whenValidInput_shouldReturnCreatedDepartment() throws Exception {
        when(departmentService.createDepartment(any(DepartmentRequestDTO.class))).thenReturn(department);
        ResultActions resultActions = mockMvc.perform(post("/api/department/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(departmentRequestDTO)));

        resultActions.andExpect(status().isCreated()) // HTTP 201
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.departmentId", is(department.getDepartmentId())))
                .andExpect(jsonPath("$.departmentName", is(department.getDepartmentName())))
                .andExpect(jsonPath("$.departmentStatus", is(department.getDepartmentStatus())));
    }

    @Test
    void createDepartment_whenNameIsEmpty_shouldReturnBadRequest() throws Exception {
        departmentRequestDTO.setDepartmentName(""); // Nombre inválido

        ResultActions resultActions = mockMvc.perform(post("/api/department/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(departmentRequestDTO)));

        // Verificar la respuesta
        resultActions.andExpect(status().isBadRequest());
    }
    
    @Test
    void createDepartment_whenNameIsDuplicate_shouldReturnConflict() throws Exception {
        // Mock del servicio para que lance la excepción de duplicado
        when(departmentService.createDepartment(any(DepartmentRequestDTO.class)))
            .thenThrow(new DuplicateDepartmentNameException("Ya existe un departamento con el nombre: " + departmentRequestDTO.getDepartmentName()));

        ResultActions resultActions = mockMvc.perform(post("/api/department/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(departmentRequestDTO)));
        
        resultActions.andExpect(status().isConflict()); 
    }


    // Pruebas para el endpoint POST /api/department/delete/{departmentId}
    @Test
    void logicallyDeleteDepartment_whenDepartmentExistsAndIsActive_shouldReturnOk() throws Exception {
        Integer departmentId = 1;
        Department inactiveDepartment = new Department();
        inactiveDepartment.setDepartmentId(departmentId);
        inactiveDepartment.setDepartmentName("Recursos Humanos");
        inactiveDepartment.setDepartmentStatus("I"); // Estado esperado

        // Configurar el mock del servicio
        when(departmentService.logicallyDeleteDepartment(eq(departmentId))).thenReturn(inactiveDepartment);

        ResultActions resultActions = mockMvc.perform(post("/api/department/delete/{departmentId}", departmentId)
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk()) 
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.departmentId", is(inactiveDepartment.getDepartmentId())))
                .andExpect(jsonPath("$.departmentStatus", is("I")));
    }

    @Test
    void logicallyDeleteDepartment_whenDepartmentNotFound_shouldReturnNotFound() throws Exception {
        Integer departmentId = 99; // ID que no existe en la base de datos

        // Mock del servicio para que lance la excepción
        when(departmentService.logicallyDeleteDepartment(eq(departmentId)))
                .thenThrow(new DepartmentNotFoundException("Departamento no encontrado con ID: " + departmentId));

        ResultActions resultActions = mockMvc.perform(post("/api/department/delete/{departmentId}", departmentId)
                .contentType(MediaType.APPLICATION_JSON));

        // Verificar la respuesta (asumiendo que GlobalExceptionHandler maneja DepartmentNotFoundException con 404)
        resultActions.andExpect(status().isNotFound());
    }

    @Test
    void logicallyDeleteDepartment_whenDepartmentAlreadyInactive_shouldReturnBadRequest() throws Exception {
        Integer departmentId = 1;

        when(departmentService.logicallyDeleteDepartment(eq(departmentId)))
                .thenThrow(new DepartmentAlreadyInactiveException("El departamento con ID: " + departmentId + " ya se encuentra inactivo."));
        
        ResultActions resultActions = mockMvc.perform(post("/api/department/delete/{departmentId}", departmentId)
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    void logicallyDeleteDepartment_whenDepartmentHasActiveEmployees_shouldReturnBadRequest() throws Exception {
        Integer departmentId = 1;

        when(departmentService.logicallyDeleteDepartment(eq(departmentId)))
                .thenThrow(new DepartmentHasActiveEmployeesException("No se puede inactivar el departamento con ID: " + departmentId + " porque tiene empleados activos."));

        ResultActions resultActions = mockMvc.perform(post("/api/department/delete/{departmentId}", departmentId)
                .contentType(MediaType.APPLICATION_JSON));
        
        resultActions.andExpect(status().isBadRequest());
    }
}