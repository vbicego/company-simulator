package de.evoila.companySimulator;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.evoila.companySimulator.controllers.ProjectController;
import de.evoila.companySimulator.models.Project;
import de.evoila.companySimulator.services.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProjectController.class)
class ProjectControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
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
    public void getAllProjectsShouldReturnFoundAndListOfProjects() throws Exception {
        ResponseEntity<?> responseEntityAnswer = new ResponseEntity<>(projectList, HttpStatus.FOUND);
        Mockito.doReturn(responseEntityAnswer).when(projectService).getAllProjects();

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/project/all"))
                .andExpect(status().isFound())
                .andExpect(content().json(objectMapper.writeValueAsString(projectList)));
    }

    @Test
    public void findProjectByIdShouldReturnFoundAndTheCorrespondentProject() throws Exception {
        ResponseEntity<?> responseEntityAnswer = new ResponseEntity<>(p1, HttpStatus.FOUND);
        Mockito.doReturn(responseEntityAnswer).when(projectService).findProjectById(1L);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/project/find/1"))
                .andExpect(status().isFound())
                .andExpect(content().json(objectMapper.writeValueAsString(p1)));
    }

    @Test
    public void createProjectShouldReturnCreatedAndTheCreatedProject() throws Exception {
        ResponseEntity<?> responseEntityAnswer = new ResponseEntity<>(p1, HttpStatus.CREATED);
        Mockito.doReturn(responseEntityAnswer).when(projectService).createProject(p1);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/project/create"))
                .andExpect(status().isFound())
                .andExpect(content().json(objectMapper.writeValueAsString(p1)));
    }

}
