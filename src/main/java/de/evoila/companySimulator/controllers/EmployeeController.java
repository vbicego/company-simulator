package de.evoila.companySimulator.controllers;

import de.evoila.companySimulator.models.Employee;
import de.evoila.companySimulator.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findEmployeeById(@PathVariable Long id) {
        return employeeService.findEmployeeById(id);
    }

    @PostMapping("/new")
    public ResponseEntity<?> createEmployee(@RequestBody @Valid Employee employeeToCreate) {
        return employeeService.createEmployee(employeeToCreate);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        return employeeService.deleteEmployee(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateEmployee(@RequestBody @Valid Employee updatedEmployee, @PathVariable Long id) {
        return employeeService.updateEmployee(updatedEmployee, id);
    }

}
