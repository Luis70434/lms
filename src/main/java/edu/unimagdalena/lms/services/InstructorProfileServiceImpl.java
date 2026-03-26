package edu.unimagdalena.lms.services;

import edu.unimagdalena.lms.api.dto.InstructorProfileDtos.InstructorProfileCreateRequest;
import edu.unimagdalena.lms.api.dto.InstructorProfileDtos.InstructorProfileResponse;
import edu.unimagdalena.lms.repositories.InstructorProfileRepository;
import edu.unimagdalena.lms.services.mapper.InstructorProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class InstructorProfileServiceImpl implements InstructorProfileService {

    private final InstructorProfileRepository repo;
    private final InstructorProfileMapper mapper;

    @Override
    public InstructorProfileResponse create(InstructorProfileCreateRequest req) {
        var profileEntity = mapper.toInstructorProfile(req);
        var entitySaved = repo.save(profileEntity);
        return mapper.toResponse(entitySaved);
    }

    @Override
    @Transactional(readOnly = true)
    public InstructorProfileResponse get(UUID id) {
        return repo.findById(id).map(mapper::toResponse)
                .orElseThrow(() -> new RuntimeException("InstructorProfile %s not found".formatted(id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<InstructorProfileResponse> list() {
        return repo.findAll().stream().map(mapper::toResponse).toList();
    }

    @Override
    public void delete(UUID id) {
        repo.deleteById(id);
    }
}