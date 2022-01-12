package de.evoila.companySimulator.services;

import de.evoila.companySimulator.exceptions.ProjectNotFoundException;
import de.evoila.companySimulator.models.Project;
import de.evoila.companySimulator.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    public ResponseEntity<?> getAllProjects() {
        return new ResponseEntity<>(projectRepository.findAll(), HttpStatus.FOUND);
    }

    public ResponseEntity<?> findProjectById(Long id) {
        return new ResponseEntity<>(projectRepository.findById(id).orElseThrow(() -> new ProjectNotFoundException(id)), HttpStatus.FOUND);
    }

    public ResponseEntity<?> createProject(Project projectToCreate) {
        return new ResponseEntity<>(projectRepository.save(projectToCreate), HttpStatus.CREATED);
    }

    public ResponseEntity<?> deleteProject(Long id) {
        projectRepository.findById(id).orElseThrow(() -> new ProjectNotFoundException(id));
        projectRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<?> updateProject(Project updatedProject, Long id) {
        Project foundProject = projectRepository.findById(id).orElseThrow(() -> new ProjectNotFoundException(id));
        foundProject.setCompanyName(updatedProject.getCompanyName());
        foundProject.setProjectName(updatedProject.getProjectName());
        return new ResponseEntity<>(projectRepository.save(foundProject), HttpStatus.OK);
    }

}
