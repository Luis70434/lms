package edu.unimagdalena.lms.test;


import edu.unimagdalena.lms.api.dto.CourseDtos.CourseCreateRequest;
import edu.unimagdalena.lms.api.dto.CourseDtos.CourseResponse;
import edu.unimagdalena.lms.entities.Course;
import edu.unimagdalena.lms.repositories.CourseRepository;
import edu.unimagdalena.lms.services.CourseServiceImpl;
import edu.unimagdalena.lms.services.mapper.CourseMapper;
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
class CourseServiceImplTest {

    @Mock private CourseRepository repo;
    @Mock private CourseMapper mapper;
    @InjectMocks private CourseServiceImpl service;

    private Course entity;
    private CourseCreateRequest request;
    private CourseResponse response;
    private UUID testId;

    @BeforeEach
    void setUp() {
        testId = UUID.randomUUID();


        request = new CourseCreateRequest("Desarrollo Web", "PUBLISHED", true);

        //  entidad
        entity = new Course();
        entity.setId(testId);
        entity.setTitle("Desarrollo Web");
        entity.setStatus("PUBLISHED");
        entity.setActive(true);

        //  respuesta
        response = new CourseResponse(testId, "Desarrollo Web", "PUBLISHED", true, Instant.now(), Instant.now());
    }

    @Test
    @DisplayName("Debe crear un curso exitosamente")
    void createTest() {
        when(mapper.toCourse(any(CourseCreateRequest.class))).thenReturn(entity);
        when(repo.save(any(Course.class))).thenReturn(entity);
        when(mapper.toResponse(any(Course.class))).thenReturn(response);

        var result = service.create(request);

        assertNotNull(result);
        assertEquals("Desarrollo Web", result.title());
        assertEquals("PUBLISHED", result.status());
        assertTrue(result.active());
        verify(repo).save(any(Course.class));
    }

    @Test
    @DisplayName("Debe obtener un curso por ID")
    void getTest() {
        when(repo.findById(testId)).thenReturn(Optional.of(entity));
        when(mapper.toResponse(entity)).thenReturn(response);

        var result = service.get(testId);

        assertNotNull(result);
        assertEquals(testId, result.id());
    }

    @Test
    @DisplayName("Debe listar todos los cursos")
    void listTest() {
        when(repo.findAll()).thenReturn(List.of(entity));
        when(mapper.toResponse(entity)).thenReturn(response);

        var result = service.list();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Debe eliminar un curso")
    void deleteTest() {
        doNothing().when(repo).deleteById(testId);
        service.delete(testId);
        verify(repo).deleteById(testId);
    }
}