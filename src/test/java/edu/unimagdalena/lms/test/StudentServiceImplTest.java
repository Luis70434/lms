package edu.unimagdalena.lms.test;



import edu.unimagdalena.lms.api.dto.StudentDtos.StudentCreateRequest;
import edu.unimagdalena.lms.api.dto.StudentDtos.StudentResponse;
import edu.unimagdalena.lms.entities.Student;
import edu.unimagdalena.lms.repositories.StudentRepository;
import edu.unimagdalena.lms.services.StudentServiceImpl;
import edu.unimagdalena.lms.services.mapper.StudentMapper;
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
class StudentServiceImplTest {

    @Mock
    private StudentRepository repo;

    @Mock
    private StudentMapper mapper;

    @InjectMocks
    private StudentServiceImpl service;

    private Student student;
    private StudentCreateRequest request;
    private StudentResponse response;
    private UUID testId;

    @BeforeEach
    void setUp() {
        testId = UUID.randomUUID();

        request = new StudentCreateRequest("estudiante@unimagdalena.edu.co", "Maria Gomez");

        student = new Student();
        student.setId(testId);
        student.setEmail("estudiante@unimagdalena.edu.co");
        student.setFullName("Maria Gomez");

        response = new StudentResponse(testId, "estudiante@unimagdalena.edu.co", "Maria Gomez", Instant.now(), Instant.now());
    }

    @Test
    @DisplayName("Debe crear un estudiante exitosamente")
    void createTest() {
        when(mapper.toEntity(any(StudentCreateRequest.class))).thenReturn(student);
        when(repo.save(any(Student.class))).thenReturn(student);
        when(mapper.toResponse(any(Student.class))).thenReturn(response);

        var result = service.create(request);

        assertNotNull(result);
        assertEquals("Maria Gomez", result.fullName());
        verify(repo).save(any(Student.class));
    }

    @Test
    @DisplayName("Debe obtener un estudiante por su ID")
    void getTest() {
        when(repo.findById(testId)).thenReturn(Optional.of(student));
        when(mapper.toResponse(student)).thenReturn(response);

        var result = service.get(testId);

        assertNotNull(result);
        assertEquals(testId, result.id());
        verify(repo).findById(testId);
    }

    @Test
    @DisplayName("Debe listar todos los estudiantes")
    void listTest() {
        when(repo.findAll()).thenReturn(List.of(student));
        when(mapper.toResponse(student)).thenReturn(response);

        var result = service.list();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(repo).findAll();
    }

    @Test
    @DisplayName("Debe eliminar un estudiante exitosamente")
    void deleteTest() {
        doNothing().when(repo).deleteById(testId);
        service.delete(testId);
        verify(repo).deleteById(testId);
    }
}