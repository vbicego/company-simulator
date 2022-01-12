package de.evoila.companySimulator.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import de.evoila.companySimulator.enums.Speciality;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @Email
    private String email;

    @NotNull
    private Speciality speciality;

    @ManyToOne
    private Project project;

    public Employee(String firstName, String lastName, String email, Speciality speciality) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.speciality = speciality;
    }
}
