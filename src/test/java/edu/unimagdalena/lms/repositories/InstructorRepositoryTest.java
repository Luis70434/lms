package edu.unimagdalena.lms.repositories;

import edu.unimagdalena.lms.empities.Instructor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;


import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@Testcontainers
@EntityScan(basePackages = "edu.unimagdalena.lms.empities")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.jpa.generate-ddl=true",
        "spring.sql.init.mode=never"
})
public class InstructorRepositoryTest{

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:16-alpine");

    @Autowired
    private InstructorRepository instructorRepository;


    private Instructor createInstructor(String email, String fullName) {
        Instructor instructor = new Instructor();
        instructor.setEmail(email);
        instructor.setFullName(fullName);
        instructor.setCreatedAt(Instant.now());
        instructor.setUpdatedAt(Instant.now());
        return instructor;
    }

    //CREATE
    @Test
    @DisplayName("Debe guardar un instructor")
    void shouldSaveInstructor() {
        Instructor instructor = createInstructor("juan@correo.com", "Juan Perez");

        Instructor saved = instructorRepository.save(instructor);

        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertEquals("juan@correo.com", saved.getEmail());
        assertEquals("Juan Perez", saved.getFullName());
    }

    //READ
    @Test
    @DisplayName("Debe buscar un instructor por id")
    void shouldFindInstructorById() {
        Instructor instructor = createInstructor("maria@correo.com", "Maria Gomez");
        Instructor saved = instructorRepository.save(instructor);

        Optional<Instructor> result = instructorRepository.findById(saved.getId());

        assertTrue(result.isPresent());
        assertEquals(saved.getId(), result.get().getId());
        assertEquals("Maria Gomez", result.get().getFullName());
    }

    @Test
    @DisplayName("Debe listar todos los instructores")
    void shouldFindAllInstructors() {
        Instructor instructor1 = createInstructor("ana@correo.com", "Ana Torres");
        Instructor instructor2 = createInstructor("carlos@correo.com", "Carlos Ruiz");

        instructorRepository.save(instructor1);
        instructorRepository.save(instructor2);

        List<Instructor> result = instructorRepository.findAll();

        assertEquals(2, result.size());
    }
    //UPDATE
    @Test
    @DisplayName("Debe actualizar un instructor")
    void shouldUpdateInstructor() {
        Instructor instructor = createInstructor("viejo@correo.com", "Nombre Viejo");
        Instructor saved = instructorRepository.save(instructor);

        saved.setEmail("nuevo@correo.com");
        saved.setFullName("Nombre Nuevo");
        saved.setUpdatedAt(Instant.now());

        Instructor updated = instructorRepository.save(saved);

        assertEquals(saved.getId(), updated.getId());
        assertEquals("nuevo@correo.com", updated.getEmail());
        assertEquals("Nombre Nuevo", updated.getFullName());
    }
    //DELETE
    @Test
    @DisplayName("Debe eliminar un instructor")
    void shouldDeleteInstructor() {
        Instructor instructor = createInstructor("borrar@correo.com", "Instructor Borrar");
        Instructor saved = instructorRepository.save(instructor);

        instructorRepository.deleteById(saved.getId());

        Optional<Instructor> deleted = instructorRepository.findById(saved.getId());
        assertFalse(deleted.isPresent());
    }

    @Test
    @DisplayName("Debe buscar instructores por fullName")
    void shouldFindByFullName() {
        Instructor instructor1 = createInstructor("luis1@correo.com", "Luis Martinez");
        Instructor instructor2 = createInstructor("luis2@correo.com", "Luis Martinez");
        Instructor instructor3 = createInstructor("pedro@correo.com", "Pedro Sanchez");

        instructorRepository.save(instructor1);
        instructorRepository.save(instructor2);
        instructorRepository.save(instructor3);

        List<Instructor> result = instructorRepository.findByFullName("Luis Martinez");

        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(i -> i.getFullName().equals("Luis Martinez")));
    }



}
