package de.evoila.companySimulator;

import de.evoila.companySimulator.enums.Speciality;
import de.evoila.companySimulator.exceptions.EmployeeNotFoundException;
import de.evoila.companySimulator.models.Employee;
import de.evoila.companySimulator.repositories.EmployeeRepository;
import de.evoila.companySimulator.services.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmployeeServiceTests {

    @MockBean
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeService employeeService;

    private List<Employee> employeeList;
    private Employee emp1;
    private Employee emp2;
    private Employee emp3;

    @BeforeEach
    public void init() {
        emp1 = new Employee("Harry", "Potter", "hp@gmail.com", Speciality.FRONTEND);
        emp2 = new Employee("Peter", "Parker", "pp@gmail.com", Speciality.BACKEND);
        emp3 = new Employee("Mary", "Jane", "mj@gmail.com", Speciality.DEVOPS);
        employeeList = List.of(emp1, emp2, emp3);
    }

    @Test
    @DisplayName("Test getAllEmployees - OK")
    public void testGetAllEmployees() {
        Mockito.when(employeeRepository.findAll()).thenReturn(employeeList);
        assertEquals(new ResponseEntity<>(employeeList, HttpStatus.FOUND), employeeService.getAllEmployees());
    }

    @Test
    @DisplayName("Test getAllEmployees - FALSE - wrong Http Status")
    public void testGetAllEmployeesWrongHttp() {
        Mockito.when(employeeRepository.findAll()).thenReturn(employeeList);
        assertNotEquals(new ResponseEntity<>(employeeList, HttpStatus.OK), employeeService.getAllEmployees());
    }

    @Test
    @DisplayName("Test getAllEmployees - FALSE - not all Employees")
    public void testGetAllEmployeesNotAllEmployees() {
        Mockito.when(employeeRepository.findAll()).thenReturn(employeeList);
        assertNotEquals(new ResponseEntity<>(emp3, HttpStatus.FOUND), employeeService.getAllEmployees());
    }

    @Test
    @DisplayName("Test findEmployeeById - OK")
    public void testFindEmployeeById() {
        Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.ofNullable(emp1));
        assertEquals(new ResponseEntity<>(emp1, HttpStatus.FOUND), employeeService.findEmployeeById(1L));
    }

    @Test
    @DisplayName("Test findEmployeeById - FALSE - wrong Http Status")
    public void testFindEmployeeByIdWrongHttp() {
        Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.ofNullable(emp1));
        assertNotEquals(new ResponseEntity<>(emp1, HttpStatus.OK), employeeService.getAllEmployees());
    }

    @Test
    @DisplayName("Test findEmployeeById - FALSE - wrong Employee")
    public void testFindEmployeeByIdWrongEmployees() {
        Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.ofNullable(emp1));
        assertNotEquals(new ResponseEntity<>(emp2, HttpStatus.FOUND), employeeService.getAllEmployees());
    }

    @Test
    @DisplayName("Test createEmployee - OK")
    public void testCreateEmployee() {
        Mockito.when(employeeRepository.save(emp1)).thenReturn(emp1);
        assertEquals(new ResponseEntity<>(emp1, HttpStatus.CREATED), employeeService.createEmployee(emp1));
    }

    @Test
    @DisplayName("Test createEmployee - FALSE - wrong Http Status")
    public void testCreateEmployeeWrongHttp() {
        Mockito.when(employeeRepository.save(emp1)).thenReturn(emp1);
        assertNotEquals(new ResponseEntity<>(emp1, HttpStatus.OK), employeeService.createEmployee(emp1));
    }

    @Test
    @DisplayName("Test createEmployee - FALSE - wrong Employee")
    public void testCreateEmployeeWrongEmployees() {
        Mockito.when(employeeRepository.save(emp1)).thenReturn(emp1);
        assertNotEquals(new ResponseEntity<>(emp2, HttpStatus.CREATED), employeeService.createEmployee(emp1));
    }

    @Test
    @DisplayName("Test deleteEmployee - OK")
    public void testDeleteEmployee() {
        Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.ofNullable(emp1));
        assertEquals(new ResponseEntity<>(HttpStatus.OK), employeeService.deleteEmployee(1L));
    }

    @Test
    @DisplayName("Test deleteEmployee - FALSE - wrong Http Status")
    public void testDeleteEmployeeWrongHttp() {
        Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.ofNullable(emp1));
        assertNotEquals(new ResponseEntity<>(emp1, HttpStatus.FOUND), employeeService.deleteEmployee(1L));
    }

    @Test
    @DisplayName("Test deleteEmployee - FALSE - employee not found")
    public void testDeleteEmployeeEmployeeNotFound() {
        Mockito.when(employeeRepository.findById(5L)).thenThrow(new EmployeeNotFoundException(5L));
        Throwable exception = assertThrows(RuntimeException.class, () -> employeeService.deleteEmployee(5L));
        assertEquals("Employee with id: 5 could not be found!", exception.getMessage());
    }

    @Test
    @DisplayName("Test updateEmployee - OK")
    public void testUpdateEmployee() {
        Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.ofNullable(emp1));
        Mockito.when(employeeRepository.save(emp1)).thenReturn(emp1);
        assertEquals(new ResponseEntity<>(emp1, HttpStatus.OK), employeeService.updateEmployee(emp1, 1L));
    }

    @Test
    @DisplayName("Test updateEmployee - FALSE - wrong Http Status")
    public void testUpdateEmployeeWrongHttp() {
        Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.ofNullable(emp1));
        Mockito.when(employeeRepository.save(emp1)).thenReturn(emp1);
        assertNotEquals(new ResponseEntity<>(emp1, HttpStatus.CREATED), employeeService.updateEmployee(emp1, 1L));
    }

    @Test
    @DisplayName("Test updateEmployee - FALSE - wrong Employee")
    public void testUpdateEmployeeWrongEmployee() {
        Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.ofNullable(emp1));
        Mockito.when(employeeRepository.save(emp1)).thenReturn(emp1);
        assertNotEquals(new ResponseEntity<>(emp2, HttpStatus.OK), employeeService.updateEmployee(emp1, 1L));
    }

    @Test
    @DisplayName("Test updateEmployee - FALSE - employee not found")
    public void testUpdateEmployeeEmployeeNotFound() {
        Mockito.when(employeeRepository.findById(5L)).thenThrow(new EmployeeNotFoundException(5L));
        Throwable exception = assertThrows(RuntimeException.class, () -> employeeService.updateEmployee(emp1, 5L));
        assertEquals("Employee with id: 5 could not be found!", exception.getMessage());
    }

}
