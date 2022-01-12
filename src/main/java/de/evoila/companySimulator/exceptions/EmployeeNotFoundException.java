package de.evoila.companySimulator.exceptions;

public class EmployeeNotFoundException extends RuntimeException {

    public EmployeeNotFoundException(Long id) {
        super("Employee with id: " + id + " could not be found!");
    }
}
