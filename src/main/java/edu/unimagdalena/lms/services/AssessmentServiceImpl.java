package edu.unimagdalena.lms.services;

import edu.unimagdalena.lms.api.dto.AssessmentDtos.AssessmentCreateRequest;
import edu.unimagdalena.lms.api.dto.AssessmentDtos.AssessmentResponse;
import edu.unimagdalena.lms.repositories.AssessmentRepository;
import edu.unimagdalena.lms.services.mapper.AssessmentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AssessmentServiceImpl implements AssessmentService {

    private final AssessmentRepository repo;
    private final AssessmentMapper mapper;

    @Override
    public AssessmentResponse create(AssessmentCreateRequest req) {
        var assessmentEntity = mapper.toAssessment(req);
        var entitySaved = repo.save(assessmentEntity);
        return mapper.toResponse(entitySaved);
    }

    @Override
    @Transactional(readOnly = true)
    public AssessmentResponse get(UUID id) {
        return repo.findById(id).map(mapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Assessment %s not found".formatted(id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssessmentResponse> list() {
        return repo.findAll().stream().map(mapper::toResponse).toList();
    }

    @Override
    public void delete(UUID id) {
        repo.deleteById(id);
    }
}