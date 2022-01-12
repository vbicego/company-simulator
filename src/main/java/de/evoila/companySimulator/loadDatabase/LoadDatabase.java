package de.evoila.companySimulator.loadDatabase;

import de.evoila.companySimulator.enums.Speciality;
import de.evoila.companySimulator.models.Employee;
import de.evoila.companySimulator.models.Project;
import de.evoila.companySimulator.repositories.EmployeeRepository;
import de.evoila.companySimulator.repositories.ProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class LoadDatabase {

    private final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(EmployeeRepository employeeRepository, ProjectRepository projectRepository) {
        return args -> {

            Employee emp1 = employeeRepository.save(new Employee("Harry", "Potter", "hp@gmail.com", Speciality.FRONTEND));
            Employee emp2 = employeeRepository.save(new Employee("Peter", "Parker", "pp@gmail.com", Speciality.BACKEND));
            Employee emp3 = employeeRepository.save(new Employee("Mary", "Jane", "mj@gmail.com", Speciality.DEVOPS));
            Employee emp4 = employeeRepository.save(new Employee("Tony", "Stark", "ts@gmail.com", Speciality.SECURITY));
            Employee emp5 = employeeRepository.save(new Employee("Andrew", "Garfield", "ag@gmail.com", Speciality.CLOUD));
            Employee emp6 = employeeRepository.save(new Employee("Tom", "Santos", "tsantos@gmail.com", Speciality.DEVOPS));

            log.info("Preloading " + emp1);
            log.info("Preloading " + emp2);
            log.info("Preloading " + emp3);
            log.info("Preloading " + emp4);
            log.info("Preloading " + emp5);
            log.info("Preloading " + emp6);

            log.info("Preloading " + projectRepository.save(new Project("Software GmbH", "HomePage", List.of(emp1, emp2, emp3))));
            log.info("Preloading " + projectRepository.save(new Project("Bank GmbH", "LoginPage", List.of(emp5, emp6))));

        };
    }

}
