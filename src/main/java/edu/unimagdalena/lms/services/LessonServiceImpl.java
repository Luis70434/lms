package edu.unimagdalena.lms.services;

import edu.unimagdalena.lms.api.dto.LessonDtos.LessonCreateRequest;
import edu.unimagdalena.lms.api.dto.LessonDtos.LessonResponse;
import edu.unimagdalena.lms.repositories.LessonRepository;
import edu.unimagdalena.lms.services.mapper.LessonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class LessonServiceImpl implements LessonService {

    private final LessonRepository repo;
    private final LessonMapper mapper;

    @Override
    public LessonResponse create(LessonCreateRequest req) {
        var lessonEntity = mapper.toLesson(req);
        var entitySaved = repo.save(lessonEntity);
        return mapper.toResponse(entitySaved);
    }

    @Override
    @Transactional(readOnly = true)
    public LessonResponse get(UUID id) {
        return repo.findById(id).map(mapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Lesson %s not found".formatted(id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<LessonResponse> list() {
        return repo.findAll().stream().map(mapper::toResponse).toList();
    }

    @Override
    public void delete(UUID id) {
        repo.deleteById(id);
    }
}