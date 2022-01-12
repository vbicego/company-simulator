package de.evoila.companySimulator.controllers;

import de.evoila.companySimulator.models.Project;
import de.evoila.companySimulator.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findProjectById(@PathVariable Long id) {
        return projectService.findProjectById(id);
    }

    @PostMapping("/new")
    public ResponseEntity<?> createProject(@RequestBody @Valid Project projectToCreate) {
        return projectService.createProject(projectToCreate);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable Long id) {
        return projectService.deleteProject(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProject(@RequestBody @Valid Project updatedProject, @PathVariable Long id) {
        return projectService.updateProject(updatedProject, id);
    }

}
