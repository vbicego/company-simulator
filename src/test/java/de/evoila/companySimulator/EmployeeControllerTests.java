package de.evoila.companySimulator;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.evoila.companySimulator.controllers.EmployeeController;
import de.evoila.companySimulator.enums.Speciality;
import de.evoila.companySimulator.exceptions.EmployeeNotFoundException;
import de.evoila.companySimulator.models.Employee;
import de.evoila.companySimulator.services.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    EmployeeService employeeService;

    private List<Employee> employeeList;
    private Employee emp1;
    private Employee emp2;

    @BeforeEach
    public void init() {
        emp1 = new Employee("Harry", "Potter", "hp@gmail.com", Speciality.FRONTEND);
        emp2 = new Employee("Peter", "Parker", "pp@gmail.com", Speciality.BACKEND);
        employeeList = List.of(emp1, emp2);
    }

    @Test
    public void getAllEmployeesShouldReturnFoundAndListOfEmployees() throws Exception {
        ResponseEntity<?> responseEntityAnswer = new ResponseEntity<>(employeeList, HttpStatus.FOUND);
        Mockito.doReturn(responseEntityAnswer).when(employeeService).getAllEmployees();

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/employee/all"))
                .andExpect(status().isFound())
                .andExpect(content().json(objectMapper.writeValueAsString(employeeList)));
    }

    @Test
    public void findEmployeeByIdShouldReturnFoundAndTheCorrespondentEmployee() throws Exception {
        ResponseEntity<?> responseEntityAnswer = new ResponseEntity<>(emp1, HttpStatus.FOUND);
        Mockito.doReturn(responseEntityAnswer).when(employeeService).findEmployeeById(1L);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/employee/find/1"))
                .andExpect(status().isFound())
                .andExpect(content().json(objectMapper.writeValueAsString(emp1)));
    }

    @Test
    public void createEmployeeShouldReturnCreatedAndTheCreatedEmployee() throws Exception {
        ResponseEntity<?> responseEntityAnswer = new ResponseEntity<>(emp2, HttpStatus.CREATED);
        Mockito.doReturn(responseEntityAnswer).when(employeeService).createEmployee(emp2);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/employee/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emp2)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(emp2)));
    }

    @Test
    public void createEmployeeShouldReturnBadRequestWhenTheDataIsInvalid() throws Exception {
        ResponseEntity<?> responseEntityAnswer = new ResponseEntity<>(emp2, HttpStatus.CREATED);
        Mockito.doReturn(responseEntityAnswer).when(employeeService).createEmployee(emp2);

        emp2.setEmail("abcabcabc");

        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/employee/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emp2)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteEmployeeShouldReturnOk() throws Exception {
        ResponseEntity<?> responseEntityAnswer = new ResponseEntity<>(HttpStatus.OK);
        Mockito.doReturn(responseEntityAnswer).when(employeeService).deleteEmployee(1L);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/employee/delete/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteEmployeeShouldReturnNotFoundWhenTheIdNotCorrespondToAnyEmployee() throws Exception {
        Mockito.when(employeeService.deleteEmployee(5L)).thenThrow(new EmployeeNotFoundException(5L));

        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/employee/delete/5"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Employee with id: " + "5" + " could not be found!"));
    }

    @Test
    public void updateEmployeeShouldReturnOkAndTheUpdatedEmployee() throws Exception {
        ResponseEntity<?> responseEntityAnswer = new ResponseEntity<>(emp1, HttpStatus.OK);
        Mockito.doReturn(responseEntityAnswer).when(employeeService).updateEmployee(emp1, 1L);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .put("/employee/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emp1)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(emp1)));
    }

    @Test
    public void updateEmployeeShouldReturnNotFoundWhenTheIdNotCorrespondToAnyEmployee() throws Exception {
        Mockito.when(employeeService.updateEmployee(emp1, 5L)).thenThrow(new EmployeeNotFoundException(5L));

        this.mockMvc.perform(MockMvcRequestBuilders
                        .put("/employee/update/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emp1)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Employee with id: " + "5" + " could not be found!"));
    }

    @Test
    public void updateEmployeeShouldReturnBadRequestWhenTheDataIsInvalid() throws Exception {
        ResponseEntity<?> responseEntityAnswer = new ResponseEntity<>(emp2, HttpStatus.OK);
        Mockito.doReturn(responseEntityAnswer).when(employeeService).updateEmployee(emp2, 2L);

        emp2.setSpeciality(null);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .put("/employee/update/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emp2)))
                .andExpect(status().isBadRequest());
    }

}
