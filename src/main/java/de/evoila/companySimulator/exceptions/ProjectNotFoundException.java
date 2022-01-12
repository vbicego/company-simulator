package de.evoila.companySimulator.exceptions;

public class ProjectNotFoundException extends RuntimeException {

    public ProjectNotFoundException(Long id) {
        super("Project with id: " + id + " could not be found!");
    }
}
