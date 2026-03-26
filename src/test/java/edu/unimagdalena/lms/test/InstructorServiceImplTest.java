package edu.unimagdalena.lms.test;

import edu.unimagdalena.lms.api.dto.InstructorDtos.InstructorCreateRequest;
import edu.unimagdalena.lms.api.dto.InstructorDtos.InstructorResponse;
import edu.unimagdalena.lms.entities.Instructor;
import edu.unimagdalena.lms.repositories.InstructorRepository;
import edu.unimagdalena.lms.services.InstructorServiceImpl;
import edu.unimagdalena.lms.services.mapper.InstructorMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InstructorServiceImplTest {

    @Mock
    private InstructorRepository repo;

    @Mock
    private InstructorMapper mapper;

    @InjectMocks
    private InstructorServiceImpl service;

    private Instructor instructor;
    private InstructorCreateRequest request;
    private InstructorResponse response;
    private UUID testId;

    @BeforeEach
    void setUp() {
        testId = UUID.randomUUID();


        request = new InstructorCreateRequest("profe@unimagdalena.edu.co", "Carlos Perez");

        instructor = new Instructor();
        instructor.setId(testId);
        instructor.setEmail("profe@unimagdalena.edu.co");
        instructor.setFullName("Carlos Perez");


        response = new InstructorResponse(testId, "profe@unimagdalena.edu.co", "Carlos Perez", Instant.now(), Instant.now());
    }

    @Test
    @DisplayName("Debe crear un instructor exitosamente")
    void createTest() {
        when(mapper.toEntity(any(InstructorCreateRequest.class))).thenReturn(instructor);
        when(repo.save(any(Instructor.class))).thenReturn(instructor);
        when(mapper.toResponse(any(Instructor.class))).thenReturn(response);

        var result = service.create(request);

        assertNotNull(result);
        // OJO: Se usa result.fullName() en vez de result.getFullName() por ser un record
        assertEquals("Carlos Perez", result.fullName());
        verify(repo).save(any(Instructor.class));
    }

    @Test
    @DisplayName("Debe obtener un instructor por su ID")
    void getTest() {
        when(repo.findById(testId)).thenReturn(Optional.of(instructor));
        when(mapper.toResponse(instructor)).thenReturn(response);

        var result = service.get(testId);

        assertNotNull(result);
        // OJO: result.id() en vez de result.getId()
        assertEquals(testId, result.id());
        verify(repo).findById(testId);
    }

    @Test
    @DisplayName("Debe listar todos los instructores")
    void listTest() {
        when(repo.findAll()).thenReturn(List.of(instructor));
        when(mapper.toResponse(instructor)).thenReturn(response);

        var result = service.list();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(repo).findAll();
    }

    @Test
    @DisplayName("Debe eliminar un instructor exitosamente")
    void deleteTest() {
        doNothing().when(repo).deleteById(testId);
        service.delete(testId);
        verify(repo).deleteById(testId);
    }
}