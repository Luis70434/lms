package edu.unimagdalena.lms.test;



import edu.unimagdalena.lms.api.dto.AssessmentDtos.AssessmentCreateRequest;
import edu.unimagdalena.lms.api.dto.AssessmentDtos.AssessmentResponse;
import edu.unimagdalena.lms.entities.Assessment;
import edu.unimagdalena.lms.repositories.AssessmentRepository;
import edu.unimagdalena.lms.services.AssessmentServiceImpl;
import edu.unimagdalena.lms.services.mapper.AssessmentMapper;
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
class AssessmentServiceImplTest {

    @Mock private AssessmentRepository repo;
    @Mock private AssessmentMapper mapper;
    @InjectMocks private AssessmentServiceImpl service;

    private Assessment entity;
    private AssessmentCreateRequest request;
    private AssessmentResponse response;
    private UUID testId;

    @BeforeEach
    void setUp() {
        testId = UUID.randomUUID();


        request = new AssessmentCreateRequest("EXAM", 95, UUID.randomUUID(), UUID.randomUUID());


        entity = new Assessment();
        entity.setId(testId);
        entity.setType("EXAM");
        entity.setScore(95);
        entity.setTakenAt(Instant.now());


        response = new AssessmentResponse(testId, "EXAM", 95, Instant.now());
    }

    @Test
    @DisplayName("Debe crear una evaluación exitosamente")
    void createTest() {
        when(mapper.toAssessment(any(AssessmentCreateRequest.class))).thenReturn(entity);
        when(repo.save(any(Assessment.class))).thenReturn(entity);
        when(mapper.toResponse(any(Assessment.class))).thenReturn(response);

        var result = service.create(request);

        assertNotNull(result);
        assertEquals("EXAM", result.type());
        assertEquals(95, result.score());
        verify(repo).save(any(Assessment.class));
    }

    @Test
    @DisplayName("Debe obtener una evaluación por ID")
    void getTest() {
        when(repo.findById(testId)).thenReturn(Optional.of(entity));
        when(mapper.toResponse(entity)).thenReturn(response);

        var result = service.get(testId);

        assertNotNull(result);
        assertEquals(testId, result.id());
    }

    @Test
    @DisplayName("Debe listar todas las evaluaciones")
    void listTest() {
        when(repo.findAll()).thenReturn(List.of(entity));
        when(mapper.toResponse(entity)).thenReturn(response);

        var result = service.list();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Debe eliminar una evaluación")
    void deleteTest() {
        doNothing().when(repo).deleteById(testId);
        service.delete(testId);
        verify(repo).deleteById(testId);
    }
}