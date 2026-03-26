package edu.unimagdalena.lms.services;

import edu.unimagdalena.lms.api.dto.CourseDtos.CourseCreateRequest;
import edu.unimagdalena.lms.api.dto.CourseDtos.CourseResponse;
import edu.unimagdalena.lms.repositories.CourseRepository;
import edu.unimagdalena.lms.services.mapper.CourseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CourseServiceImpl implements CourseService {

    private final CourseRepository repo;
    private final CourseMapper mapper;

    @Override
    public CourseResponse create(CourseCreateRequest req) {
        var courseEntity = mapper.toCourse(req);
        var entitySaved = repo.save(courseEntity);
        return mapper.toResponse(entitySaved);
    }

    @Override
    @Transactional(readOnly = true)
    public CourseResponse get(UUID id) {
        return repo.findById(id).map(mapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Course %s not found".formatted(id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseResponse> list() {
        return repo.findAll().stream().map(mapper::toResponse).toList();
    }

    @Override
    public void delete(UUID id) {
        repo.deleteById(id);
    }
}