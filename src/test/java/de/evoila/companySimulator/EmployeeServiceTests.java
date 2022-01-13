package de.evoila.companySimulator;

import de.evoila.companySimulator.enums.Speciality;
import de.evoila.companySimulator.exceptions.EmployeeNotFoundException;
import de.evoila.companySimulator.models.Employee;
import de.evoila.companySimulator.repositories.EmployeeRepository;
import de.evoila.companySimulator.services.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
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
    public void getAllEmployeesShouldReturnFoundAndListOfEmployees() {
        Mockito.when(employeeRepository.findAll()).thenReturn(employeeList);
        assertEquals(new ResponseEntity<>(employeeList, HttpStatus.FOUND), employeeService.getAllEmployees());
    }

    @Test
    public void getAllEmployeesShouldReturnFound() {
        Mockito.when(employeeRepository.findAll()).thenReturn(employeeList);
        assertNotEquals(new ResponseEntity<>(employeeList, HttpStatus.OK), employeeService.getAllEmployees());
    }

    @Test
    public void getAllEmployeesShouldReturnAllSavedEmployees() {
        Mockito.when(employeeRepository.findAll()).thenReturn(employeeList);
        assertNotEquals(new ResponseEntity<>(emp3, HttpStatus.FOUND), employeeService.getAllEmployees());
    }

    @Test
    public void findEmployeeByIdShouldReturnFoundAndAnEmployee() {
        Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.ofNullable(emp1));
        assertEquals(new ResponseEntity<>(emp1, HttpStatus.FOUND), employeeService.findEmployeeById(1L));
    }

    @Test
    public void findEmployeeByIdShouldReturnFound() {
        Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.ofNullable(emp1));
        assertNotEquals(new ResponseEntity<>(emp1, HttpStatus.OK), employeeService.getAllEmployees());
    }

    @Test
    public void findEmployeesByIdShouldReturnTheEmployeeCorrespondentToTheGivenId() {
        Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.ofNullable(emp1));
        assertNotEquals(new ResponseEntity<>(emp2, HttpStatus.FOUND), employeeService.getAllEmployees());
    }

    @Test
    public void createEmployeeShouldReturnOkAndTheCreatedEmployee() {
        Mockito.when(employeeRepository.save(emp1)).thenReturn(emp1);
        assertEquals(new ResponseEntity<>(emp1, HttpStatus.CREATED), employeeService.createEmployee(emp1));
    }

    @Test
    public void createEmployeeShouldReturnOk() {
        Mockito.when(employeeRepository.save(emp1)).thenReturn(emp1);
        assertNotEquals(new ResponseEntity<>(emp1, HttpStatus.OK), employeeService.createEmployee(emp1));
    }

    @Test
    public void createEmployeeShouldReturnTheCreatedEmployee() {
        Mockito.when(employeeRepository.save(emp1)).thenReturn(emp1);
        assertNotEquals(new ResponseEntity<>(emp2, HttpStatus.CREATED), employeeService.createEmployee(emp1));
    }

    @Test
    public void deleteEmployeeShouldReturnOk() {
        Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.ofNullable(emp1));
        assertEquals(new ResponseEntity<>(HttpStatus.OK), employeeService.deleteEmployee(1L));
    }

    @Test
    public void deleteEmployeeShouldNotReturnFoundOrOtherHttpStatus() {
        Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.ofNullable(emp1));
        assertNotEquals(new ResponseEntity<>(emp1, HttpStatus.FOUND), employeeService.deleteEmployee(1L));
    }

    @Test
    public void deleteEmployeeThrowAnExceptionWhenTheGivenIdNotCorrespondToAnyEmployee() {
        Mockito.when(employeeRepository.findById(5L)).thenThrow(new EmployeeNotFoundException(5L));
        Throwable exception = assertThrows(RuntimeException.class, () -> employeeService.deleteEmployee(5L));
        assertEquals("Employee with id: 5 could not be found!", exception.getMessage());
    }

    @Test
    public void updateEmployeeShouldReturnOkAndTheUpdatedEmployee() {
        Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.ofNullable(emp1));
        Mockito.when(employeeRepository.save(emp1)).thenReturn(emp1);
        assertEquals(new ResponseEntity<>(emp1, HttpStatus.OK), employeeService.updateEmployee(emp1, 1L));
    }

    @Test
    public void updateEmployeeShouldReturnOk() {
        Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.ofNullable(emp1));
        Mockito.when(employeeRepository.save(emp1)).thenReturn(emp1);
        assertNotEquals(new ResponseEntity<>(emp1, HttpStatus.CREATED), employeeService.updateEmployee(emp1, 1L));
    }

    @Test
    public void updateEmployeeShoudlReturnTheUpdatedEmployee() {
        Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.ofNullable(emp1));
        Mockito.when(employeeRepository.save(emp1)).thenReturn(emp1);
        assertNotEquals(new ResponseEntity<>(emp2, HttpStatus.OK), employeeService.updateEmployee(emp1, 1L));
    }

    @Test
    public void updateEmployeeThrowAnExceptionWhenTheGivenIdNotCorrespondToAnyEmployee() {
        Mockito.when(employeeRepository.findById(5L)).thenThrow(new EmployeeNotFoundException(5L));
        Throwable exception = assertThrows(RuntimeException.class, () -> employeeService.updateEmployee(emp1, 5L));
        assertEquals("Employee with id: 5 could not be found!", exception.getMessage());
    }

}
