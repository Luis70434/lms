package edu.unimagdalena.lms.test;


import edu.unimagdalena.lms.api.dto.LessonDtos.LessonCreateRequest;
import edu.unimagdalena.lms.api.dto.LessonDtos.LessonResponse;
import edu.unimagdalena.lms.entities.Lesson;
import edu.unimagdalena.lms.repositories.LessonRepository;
import edu.unimagdalena.lms.services.LessonServiceImpl;
import edu.unimagdalena.lms.services.mapper.LessonMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LessonServiceImplTest {

    @Mock private LessonRepository repo;
    @Mock private LessonMapper mapper;
    @InjectMocks private LessonServiceImpl service;

    private Lesson entity;
    private LessonCreateRequest request;
    private LessonResponse response;
    private UUID testId;

    @BeforeEach
    void setUp() {
        testId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();


        request = new LessonCreateRequest("Introducción a JPA", 1, courseId);


        entity = new Lesson();
        entity.setId(testId);
        entity.setTitle("Introducción a JPA");
        entity.setOrderIndex(1);

        // Respuesta
        response = new LessonResponse(testId, "Introducción a JPA", 1);
    }

    @Test
    @DisplayName("Debe crear una lección exitosamente")
    void createTest() {
        when(mapper.toLesson(any(LessonCreateRequest.class))).thenReturn(entity);
        when(repo.save(any(Lesson.class))).thenReturn(entity);
        when(mapper.toResponse(any(Lesson.class))).thenReturn(response);

        var result = service.create(request);

        assertNotNull(result);
        assertEquals("Introducción a JPA", result.title());
        assertEquals(1, result.orderIndex());
        verify(repo).save(any(Lesson.class));
    }

    @Test
    @DisplayName("Debe obtener una lección por ID")
    void getTest() {
        when(repo.findById(testId)).thenReturn(Optional.of(entity));
        when(mapper.toResponse(entity)).thenReturn(response);

        var result = service.get(testId);

        assertNotNull(result);
        assertEquals(testId, result.id());
    }

    @Test
    @DisplayName("Debe listar todas las lecciones")
    void listTest() {
        when(repo.findAll()).thenReturn(List.of(entity));
        when(mapper.toResponse(entity)).thenReturn(response);

        var result = service.list();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Debe eliminar una lección")
    void deleteTest() {
        doNothing().when(repo).deleteById(testId);
        service.delete(testId);
        verify(repo).deleteById(testId);
    }
}