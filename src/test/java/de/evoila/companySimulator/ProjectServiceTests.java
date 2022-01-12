package de.evoila.companySimulator;

import de.evoila.companySimulator.exceptions.EmployeeNotFoundException;
import de.evoila.companySimulator.models.Project;
import de.evoila.companySimulator.repositories.ProjectRepository;
import de.evoila.companySimulator.services.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("Test getAllProjects - OK")
    public void testGetAllProjects() {
        Mockito.when(projectRepository.findAll()).thenReturn(projectList);
        assertEquals(new ResponseEntity<>(projectList, HttpStatus.FOUND), projectService.getAllProjects());
    }

    @Test
    @DisplayName("Test getAllProjects - FALSE - wrong Http Status")
    public void testGetAllProjectsWrongHttp() {
        Mockito.when(projectRepository.findAll()).thenReturn(projectList);
        assertNotEquals(new ResponseEntity<>(projectList, HttpStatus.OK), projectService.getAllProjects());
    }

    @Test
    @DisplayName("Test getAllProjects - FALSE - not all Projects")
    public void testGetAllProjectsNotAllProjects() {
        Mockito.when(projectRepository.findAll()).thenReturn(projectList);
        assertNotEquals(new ResponseEntity<>(p2, HttpStatus.FOUND), projectService.getAllProjects());
    }

    @Test
    @DisplayName("Test findProjectById - OK")
    public void testFindProjectById() {
        Mockito.when(projectRepository.findById(1L)).thenReturn(Optional.ofNullable(p1));
        assertEquals(new ResponseEntity<>(p1, HttpStatus.FOUND), projectService.findProjectById(1L));
    }

    @Test
    @DisplayName("Test findProjectById - FALSE - wrong Http Status")
    public void testFindProjectByIdWrongHttp() {
        Mockito.when(projectRepository.findById(1L)).thenReturn(Optional.ofNullable(p1));
        assertNotEquals(new ResponseEntity<>(p1, HttpStatus.OK), projectService.findProjectById(1L));
    }

    @Test
    @DisplayName("Test findProjectById - FALSE - wrong Project")
    public void testFindProjectByIdWrongProject() {
        Mockito.when(projectRepository.findById(1L)).thenReturn(Optional.ofNullable(p1));
        assertNotEquals(new ResponseEntity<>(p2, HttpStatus.FOUND), projectService.findProjectById(1L));
    }

    @Test
    @DisplayName("Test createProject - OK")
    public void testCreateProject() {
        Mockito.when(projectRepository.save(p1)).thenReturn(p1);
        assertEquals(new ResponseEntity<>(p1, HttpStatus.CREATED), projectService.createProject(p1));
    }

    @Test
    @DisplayName("Test createProject - FALSE - wrong Http Status")
    public void testCreateProjectWrongHttp() {
        Mockito.when(projectRepository.save(p1)).thenReturn(p1);
        assertNotEquals(new ResponseEntity<>(p1, HttpStatus.OK), projectService.createProject(p1));
    }

    @Test
    @DisplayName("Test createProject - FALSE - wrong Employee")
    public void testCreateProjectWrongEmployees() {
        Mockito.when(projectRepository.save(p1)).thenReturn(p1);
        assertNotEquals(new ResponseEntity<>(p2, HttpStatus.CREATED), projectService.createProject(p1));
    }

    @Test
    @DisplayName("Test deleteProject - OK")
    public void testDeleteProject() {
        Mockito.when(projectRepository.findById(1L)).thenReturn(Optional.ofNullable(p1));
        assertEquals(new ResponseEntity<>(HttpStatus.OK), projectService.deleteProject(1L));
    }

    @Test
    @DisplayName("Test deleteProject - FALSE - wrong Http Status")
    public void testDeleteProjectWrongHttp() {
        Mockito.when(projectRepository.findById(1L)).thenReturn(Optional.ofNullable(p1));
        assertNotEquals(new ResponseEntity<>(p1, HttpStatus.FOUND), projectService.deleteProject(1L));
    }

    @Test
    @DisplayName("Test deleteProject - FALSE - project not found")
    public void testDeleteProjectProjectNotFound() {
        Mockito.when(projectRepository.findById(1L)).thenReturn(Optional.ofNullable(p1));
        Throwable exception = assertThrows(RuntimeException.class, () -> projectService.deleteProject(5L));
        assertEquals("Project with id: 5 could not be found!", exception.getMessage());
    }

    @Test
    @DisplayName("Test updateProject - OK")
    public void testUpdateProject() {
        Mockito.when(projectRepository.findById(1L)).thenReturn(Optional.ofNullable(p1));
        Mockito.when(projectRepository.save(p1)).thenReturn(p1);
        assertEquals(new ResponseEntity<>(p1, HttpStatus.OK), projectService.updateProject(p1, 1L));
    }

    @Test
    @DisplayName("Test updateProject - FALSE - wrong Http Status")
    public void testUpdateProjectWrongHttp() {
        Mockito.when(projectRepository.findById(1L)).thenReturn(Optional.ofNullable(p1));
        Mockito.when(projectRepository.save(p1)).thenReturn(p1);
        assertNotEquals(new ResponseEntity<>(p1, HttpStatus.CREATED), projectService.updateProject(p1, 1L));
    }

    @Test
    @DisplayName("Test updateProject - FALSE - wrong project")
    public void testUpdateProjectWrongProject() {
        Mockito.when(projectRepository.findById(1L)).thenReturn(Optional.ofNullable(p1));
        Mockito.when(projectRepository.save(p1)).thenReturn(p1);
        assertNotEquals(new ResponseEntity<>(p2, HttpStatus.OK), projectService.updateProject(p1, 1L));
    }

    @Test
    @DisplayName("Test updateProject - FALSE - project not found")
    public void testUpdateProjectProjectNotFound() {
        Mockito.when(projectRepository.findById(5L)).thenThrow(new EmployeeNotFoundException(5L));
        Throwable exception = assertThrows(RuntimeException.class, () -> projectService.updateProject(p1, 5L));
        assertEquals("Employee with id: 5 could not be found!", exception.getMessage());
    }

}
