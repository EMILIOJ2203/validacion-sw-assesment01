package com.viraj.sample.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viraj.sample.entity.Employee;
import com.viraj.sample.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    private Employee employee;
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
        objectMapper = new ObjectMapper();

        employee = new Employee("Nicanor Pérez", "Desarrollador Senior");
        employee.setEmployeeId(1L);
    }

    @Test
    public void testSaveEmployee_Success() throws Exception {
        // Arrange
        Employee newEmployee = new Employee("Carlos López", "Ingeniero QA");
        newEmployee.setEmployeeId(1L);
        when(employeeService.saveEmployee(any(Employee.class))).thenReturn(newEmployee);

        // Act & Assert
        mockMvc.perform(post("/employee/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newEmployee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeId", is(1)))
                .andExpect(jsonPath("$.employeeName", is("Carlos López")))
                .andExpect(jsonPath("$.employeeDescription", is("Ingeniero QA")));

        verify(employeeService, times(1)).saveEmployee(any(Employee.class));
    }

    @Test
    public void testUpdateEmployee_Success() throws Exception {
        // Arrange
        employee.setEmployeeName("Nicanor Pérez Actualizado");
        when(employeeService.updateEmployee(any(Employee.class))).thenReturn(employee);

        // Act & Assert
        mockMvc.perform(put("/employee/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeId", is(1)))
                .andExpect(jsonPath("$.employeeName", is("Nicanor Pérez Actualizado")))
                .andExpect(jsonPath("$.employeeDescription", is("Desarrollador Senior")));

        verify(employeeService, times(1)).updateEmployee(any(Employee.class));
    }

    @Test
    public void testDeleteEmployee_Success() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/employee/delete/1"))
                .andExpect(status().isOk());

        verify(employeeService, times(1)).deleteEmployee(1L);
    }

}
