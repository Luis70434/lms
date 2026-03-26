package edu.unimagdalena.lms.services;

import edu.unimagdalena.lms.api.dto.StudentDtos.StudentCreateRequest;
import edu.unimagdalena.lms.api.dto.StudentDtos.StudentResponse;
import edu.unimagdalena.lms.repositories.StudentRepository;
import edu.unimagdalena.lms.services.mapper.StudentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repo;
    private final StudentMapper mapper;

    @Override
    public StudentResponse create(StudentCreateRequest req) {
        var studentEntity = mapper.toEntity(req);
        var entitySaved = repo.save(studentEntity);
        var studentDtoResponse = mapper.toResponse(entitySaved);
        return studentDtoResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public StudentResponse get(UUID id) {
        return repo.findById(id).map(mapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Student %s not found".formatted(id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentResponse> list() {
        return repo.findAll().stream().map(mapper::toResponse).toList();
    }

    @Override
    public void delete(UUID id) {
        repo.deleteById(id);
    }
}