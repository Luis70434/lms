package edu.unimagdalena.lms.services;

import edu.unimagdalena.lms.api.dto.InstructorDtos.InstructorCreateRequest;
import edu.unimagdalena.lms.api.dto.InstructorDtos.InstructorResponse;
import edu.unimagdalena.lms.repositories.InstructorRepository;
import edu.unimagdalena.lms.services.mapper.InstructorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class InstructorServiceImpl implements InstructorService {

    private final InstructorRepository repo;
    private final InstructorMapper mapper;

    @Override
    public InstructorResponse create(InstructorCreateRequest req) {
        var instructorEntity = mapper.toEntity(req);
        var entitySaved = repo.save(instructorEntity);
        var instructorDtoResponse = mapper.toResponse(entitySaved);
        return instructorDtoResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public InstructorResponse get(UUID id) {
        return repo.findById(id).map(mapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Instructor %s not found".formatted(id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<InstructorResponse> list() {
        return repo.findAll().stream().map(mapper::toResponse).toList();
    }

    @Override
    public void delete(UUID id) {
        repo.deleteById(id);
    }
}