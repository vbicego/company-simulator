package de.evoila.companySimulator;

import de.evoila.companySimulator.exceptions.EmployeeNotFoundException;
import de.evoila.companySimulator.models.Project;
import de.evoila.companySimulator.repositories.ProjectRepository;
import de.evoila.companySimulator.services.ProjectService;
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
class ProjectServiceTests {

    @MockBean
    ProjectRepository projectRepository;

    @Autowired
    ProjectService projectService;

    private List<Project> projectList;
    private Project p1;
    private Project p2;

    @BeforeEach
    public void init() {
        p1 = new Project("WagenDesVolkes", "HomePage");
        p2 = new Project("Tosch", "Configuration");
        projectList = List.of(p1, p2);
    }

    @Test
    public void getAllProjectsShouldReturnFoundAndListOfProjects() {
        Mockito.when(projectRepository.findAll()).thenReturn(projectList);
        assertEquals(new ResponseEntity<>(projectList, HttpStatus.FOUND), projectService.getAllProjects());
    }

    @Test
    public void getAllProjectsShouldReturnFound() {
        Mockito.when(projectRepository.findAll()).thenReturn(projectList);
        assertNotEquals(new ResponseEntity<>(projectList, HttpStatus.OK), projectService.getAllProjects());
    }

    @Test
    public void getAllProjectsShouldReturnAllSavedProjects() {
        Mockito.when(projectRepository.findAll()).thenReturn(projectList);
        assertNotEquals(new ResponseEntity<>(p2, HttpStatus.FOUND), projectService.getAllProjects());
    }

    @Test
    public void findProjectByIdShouldReturnFoundAndTheFoundProject() {
        Mockito.when(projectRepository.findById(1L)).thenReturn(Optional.ofNullable(p1));
        assertEquals(new ResponseEntity<>(p1, HttpStatus.FOUND), projectService.findProjectById(1L));
    }

    @Test
    public void findProjectByIdShouldReturnFound() {
        Mockito.when(projectRepository.findById(1L)).thenReturn(Optional.ofNullable(p1));
        assertNotEquals(new ResponseEntity<>(p1, HttpStatus.OK), projectService.findProjectById(1L));
    }

    @Test
    public void findProjectByIdShouldReturnTheFoundProject() {
        Mockito.when(projectRepository.findById(1L)).thenReturn(Optional.ofNullable(p1));
        assertNotEquals(new ResponseEntity<>(p2, HttpStatus.FOUND), projectService.findProjectById(1L));
    }

    @Test
    public void createProjectShouldReturnCreatedAndTheCreatedProject() {
        Mockito.when(projectRepository.save(p1)).thenReturn(p1);
        assertEquals(new ResponseEntity<>(p1, HttpStatus.CREATED), projectService.createProject(p1));
    }

    @Test
    public void createProjectShouldReturnCreated() {
        Mockito.when(projectRepository.save(p1)).thenReturn(p1);
        assertNotEquals(new ResponseEntity<>(p1, HttpStatus.OK), projectService.createProject(p1));
    }

    @Test
    public void createProjectShouldReturnTheCreatedProject() {
        Mockito.when(projectRepository.save(p1)).thenReturn(p1);
        assertNotEquals(new ResponseEntity<>(p2, HttpStatus.CREATED), projectService.createProject(p1));
    }

    @Test
    public void deleteProjectShouldReturnOk() {
        Mockito.when(projectRepository.findById(1L)).thenReturn(Optional.ofNullable(p1));
        assertEquals(new ResponseEntity<>(HttpStatus.OK), projectService.deleteProject(1L));
    }

    @Test
    public void deleteProjectShouldNotReturnFoundOrOtherHttpStatus() {
        Mockito.when(projectRepository.findById(1L)).thenReturn(Optional.ofNullable(p1));
        assertNotEquals(new ResponseEntity<>(p1, HttpStatus.FOUND), projectService.deleteProject(1L));
    }

    @Test
    public void deleteProjectThrowAnExceptionWhenTheGivenIdNotCorrespondToAnyProject() {
        Mockito.when(projectRepository.findById(1L)).thenReturn(Optional.ofNullable(p1));
        Throwable exception = assertThrows(RuntimeException.class, () -> projectService.deleteProject(5L));
        assertEquals("Project with id: 5 could not be found!", exception.getMessage());
    }

    @Test
    public void updateProjectShouldReturnOkAndTheUpdatedProject() {
        Mockito.when(projectRepository.findById(1L)).thenReturn(Optional.ofNullable(p1));
        Mockito.when(projectRepository.save(p1)).thenReturn(p1);
        assertEquals(new ResponseEntity<>(p1, HttpStatus.OK), projectService.updateProject(p1, 1L));
    }

    @Test
    public void updateProjectShouldReturnOk() {
        Mockito.when(projectRepository.findById(1L)).thenReturn(Optional.ofNullable(p1));
        Mockito.when(projectRepository.save(p1)).thenReturn(p1);
        assertNotEquals(new ResponseEntity<>(p1, HttpStatus.CREATED), projectService.updateProject(p1, 1L));
    }

    @Test
    public void updateProjectShouldReturnTheUpdatedProject() {
        Mockito.when(projectRepository.findById(1L)).thenReturn(Optional.ofNullable(p1));
        Mockito.when(projectRepository.save(p1)).thenReturn(p1);
        assertNotEquals(new ResponseEntity<>(p2, HttpStatus.OK), projectService.updateProject(p1, 1L));
    }

    @Test
    public void updateProjectThrowAnExceptionWhenTheGivenIdNotCorrespondToAnyProject() {
        Mockito.when(projectRepository.findById(5L)).thenThrow(new EmployeeNotFoundException(5L));
        Throwable exception = assertThrows(RuntimeException.class, () -> projectService.updateProject(p1, 5L));
        assertEquals("Employee with id: 5 could not be found!", exception.getMessage());
    }

}
