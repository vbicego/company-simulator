package de.evoila.companySimulator.controllers;

import de.evoila.companySimulator.exceptions.EmployeeNotFoundException;
import de.evoila.companySimulator.models.Employee;
import de.evoila.companySimulator.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;

    @GetMapping("/all")
    public ResponseEntity<?> getAllEmployees() {
        return new ResponseEntity<>(employeeRepository.findAll(), HttpStatus.FOUND);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findEmployeeById(@PathVariable Long id) {
        return new ResponseEntity<>(employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id)), HttpStatus.FOUND);
    }

    @PostMapping("/new")
    public ResponseEntity<?> createEmployee(@RequestBody @Valid Employee employeeToCreate) {
        return new ResponseEntity<>(employeeRepository.save(employeeToCreate), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        employeeRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateEmployee(@RequestBody @Valid Employee updatedEmployee, @PathVariable Long id) {

        Employee foundEmployee = employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));

        foundEmployee.setFirstName(updatedEmployee.getFirstName());
        foundEmployee.setLastName(updatedEmployee.getLastName());
        foundEmployee.setEmail(updatedEmployee.getEmail());
        foundEmployee.setSpeciality(updatedEmployee.getSpeciality());
        foundEmployee.setProject(updatedEmployee.getProject());

        return new ResponseEntity<>(employeeRepository.save(foundEmployee), HttpStatus.OK);
    }

}
