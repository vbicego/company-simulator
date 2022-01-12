package de.evoila.companySimulator.controllers;

import de.evoila.companySimulator.exceptions.ProjectNotFoundException;
import de.evoila.companySimulator.models.Project;
import de.evoila.companySimulator.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    ProjectRepository projectRepository;

    @GetMapping("/all")
    public ResponseEntity<?> getAllProjects() {
        return new ResponseEntity<>(projectRepository.findAll(), HttpStatus.FOUND);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findProjectById(@PathVariable Long id) {
        return new ResponseEntity<>(projectRepository.findById(id).orElseThrow(() -> new ProjectNotFoundException(id)), HttpStatus.FOUND);
    }

    @PostMapping("/new")
    public ResponseEntity<?> createProject(@RequestBody @Valid Project projectToCreate) {
        return new ResponseEntity<>(projectRepository.save(projectToCreate), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable Long id) {
        projectRepository.findById(id).orElseThrow(() -> new ProjectNotFoundException(id));
        projectRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProject(@RequestBody @Valid Project updatedProject, @PathVariable Long id) {

        Project foundProject = projectRepository.findById(id).orElseThrow(() -> new ProjectNotFoundException(id));

        foundProject.setCompanyName(updatedProject.getCompanyName());
        foundProject.setProjectName(updatedProject.getProjectName());
        foundProject.setEmployeeList(updatedProject.getEmployeeList());

        return new ResponseEntity<>(projectRepository.save(foundProject), HttpStatus.OK);
    }

}
