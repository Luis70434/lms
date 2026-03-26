package edu.unimagdalena.lms.test;



import edu.unimagdalena.lms.api.dto.InstructorProfileDtos.InstructorProfileCreateRequest;
import edu.unimagdalena.lms.api.dto.InstructorProfileDtos.InstructorProfileResponse;
import edu.unimagdalena.lms.entities.InstructorProfile;
import edu.unimagdalena.lms.repositories.InstructorProfileRepository;
import edu.unimagdalena.lms.services.InstructorProfileServiceImpl;
import edu.unimagdalena.lms.services.mapper.InstructorProfileMapper;
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
class InstructorProfileServiceImplTest {

    @Mock private InstructorProfileRepository repo;
    @Mock private InstructorProfileMapper mapper;
    @InjectMocks private InstructorProfileServiceImpl service;

    private InstructorProfile entity;
    private InstructorProfileCreateRequest request;
    private InstructorProfileResponse response;
    private UUID testId;

    @BeforeEach
    void setUp() {
        testId = UUID.randomUUID();


        request = new InstructorProfileCreateRequest("+57 3001234567", "Ingeniero de Software con 10 años de experiencia.", UUID.randomUUID());


        entity = new InstructorProfile();
        entity.setId(testId);
        entity.setPhone("+57 3001234567");
        entity.setBio("Ingeniero de Software con 10 años de experiencia.");


        response = new InstructorProfileResponse(testId, "+57 3001234567", "Ingeniero de Software con 10 años de experiencia.");
    }

    @Test
    @DisplayName("Debe crear un perfil de instructor exitosamente")
    void createTest() {
        when(mapper.toInstructorProfile(any(InstructorProfileCreateRequest.class))).thenReturn(entity);
        when(repo.save(any(InstructorProfile.class))).thenReturn(entity);
        when(mapper.toResponse(any(InstructorProfile.class))).thenReturn(response);

        var result = service.create(request);

        assertNotNull(result);
        assertEquals("+57 3001234567", result.phone());
        assertEquals("Ingeniero de Software con 10 años de experiencia.", result.bio());
        verify(repo).save(any(InstructorProfile.class));
    }

    @Test
    @DisplayName("Debe obtener un perfil de instructor por ID")
    void getTest() {
        when(repo.findById(testId)).thenReturn(Optional.of(entity));
        when(mapper.toResponse(entity)).thenReturn(response);

        var result = service.get(testId);

        assertNotNull(result);
        assertEquals(testId, result.id());
    }

    @Test
    @DisplayName("Debe listar todos los perfiles de instructores")
    void listTest() {
        when(repo.findAll()).thenReturn(List.of(entity));
        when(mapper.toResponse(entity)).thenReturn(response);

        var result = service.list();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Debe eliminar un perfil de instructor")
    void deleteTest() {
        doNothing().when(repo).deleteById(testId);
        service.delete(testId);
        verify(repo).deleteById(testId);
    }
}