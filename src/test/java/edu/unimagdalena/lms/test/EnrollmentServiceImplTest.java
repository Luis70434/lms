package edu.unimagdalena.lms.test;


import edu.unimagdalena.lms.api.dto.EnrollmentDtos.EnrollmentCreateRequest;
import edu.unimagdalena.lms.api.dto.EnrollmentDtos.EnrollmentResponse;
import edu.unimagdalena.lms.entities.Enrollment;
import edu.unimagdalena.lms.repositories.EnrollmentRepository;
import edu.unimagdalena.lms.services.EnrollmentServiceImpl;
import edu.unimagdalena.lms.services.mapper.EnrollmentMapper;
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
class EnrollmentServiceImplTest {

    @Mock private EnrollmentRepository repo;
    @Mock private EnrollmentMapper mapper;
    @InjectMocks private EnrollmentServiceImpl service;

    private Enrollment entity;
    private EnrollmentCreateRequest request;
    private EnrollmentResponse response;
    private UUID testId;

    @BeforeEach
    void setUp() {
        testId = UUID.randomUUID();


        request = new EnrollmentCreateRequest("ACTIVE", UUID.randomUUID(), UUID.randomUUID());


        entity = new Enrollment();
        entity.setId(testId);
        entity.setStatus("ACTIVE");
        entity.setEnrolledAt(Instant.now());


        response = new EnrollmentResponse(testId, "ACTIVE", Instant.now());
    }

    @Test
    @DisplayName("Debe crear una matrícula exitosamente")
    void createTest() {
        when(mapper.toEnrollment(any(EnrollmentCreateRequest.class))).thenReturn(entity);
        when(repo.save(any(Enrollment.class))).thenReturn(entity);
        when(mapper.toResponse(any(Enrollment.class))).thenReturn(response);

        var result = service.create(request);

        assertNotNull(result);
        assertEquals("ACTIVE", result.status());
        verify(repo).save(any(Enrollment.class));
    }

    @Test
    @DisplayName("Debe obtener una matrícula por ID")
    void getTest() {
        when(repo.findById(testId)).thenReturn(Optional.of(entity));
        when(mapper.toResponse(entity)).thenReturn(response);

        var result = service.get(testId);

        assertNotNull(result);
        assertEquals(testId, result.id());
    }

    @Test
    @DisplayName("Debe listar todas las matrículas")
    void listTest() {
        when(repo.findAll()).thenReturn(List.of(entity));
        when(mapper.toResponse(entity)).thenReturn(response);

        var result = service.list();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Debe eliminar una matrícula")
    void deleteTest() {
        doNothing().when(repo).deleteById(testId);
        service.delete(testId);
        verify(repo).deleteById(testId);
    }
}