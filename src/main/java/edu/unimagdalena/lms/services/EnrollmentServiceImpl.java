package edu.unimagdalena.lms.services;

import edu.unimagdalena.lms.api.dto.EnrollmentDtos.EnrollmentCreateRequest;
import edu.unimagdalena.lms.api.dto.EnrollmentDtos.EnrollmentResponse;
import edu.unimagdalena.lms.repositories.EnrollmentRepository;
import edu.unimagdalena.lms.services.mapper.EnrollmentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository repo;
    private final EnrollmentMapper mapper;

    @Override
    public EnrollmentResponse create(EnrollmentCreateRequest req) {
        var enrollmentEntity = mapper.toEnrollment(req);
        var entitySaved = repo.save(enrollmentEntity);
        return mapper.toResponse(entitySaved);
    }

    @Override
    @Transactional(readOnly = true)
    public EnrollmentResponse get(UUID id) {
        return repo.findById(id).map(mapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Enrollment %s not found".formatted(id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EnrollmentResponse> list() {
        return repo.findAll().stream().map(mapper::toResponse).toList();
    }

    @Override
    public void delete(UUID id) {
        repo.deleteById(id);
    }
}