package com.viraj.sample.service;

import com.viraj.sample.entity.Employee;
import com.viraj.sample.repository.EmployeeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @Before
    public void setUp() {
        employee = new Employee("Nicanor Pérez", "Desarrollador Senior");
        employee.setEmployeeId(1L);
    }

    @Test
    public void testSaveEmployee_Success() {
        // Arrange
        Employee newEmployee = new Employee("Carlos López", "Ingeniero QA");
        when(employeeRepository.save(newEmployee)).thenReturn(newEmployee);

        // Act
        Employee savedEmployee = employeeService.saveEmployee(newEmployee);

        // Assert
        assertNotNull(savedEmployee);
        assertEquals("Carlos López", savedEmployee.getEmployeeName());
        verify(employeeRepository, times(1)).save(newEmployee);
    }

    @Test
    public void testUpdateEmployee_Success() {
        // Arrange
        employee.setEmployeeName("Nicanor Pérez Actualizado");
        when(employeeRepository.save(employee)).thenReturn(employee);

        // Act
        Employee updatedEmployee = employeeService.updateEmployee(employee);

        // Assert
        assertNotNull(updatedEmployee);
        assertEquals("Nicanor Pérez Actualizado", updatedEmployee.getEmployeeName());
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    public void testDeleteEmployee_Success() {
        // Act
        employeeService.deleteEmployee(1L);

        // Assert
        verify(employeeRepository, times(1)).deleteById(1L);
    }


}
