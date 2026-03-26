package edu.unimagdalena.lms.services;

import edu.unimagdalena.lms.api.dto.LessonDtos.LessonCreateRequest;
import edu.unimagdalena.lms.api.dto.LessonDtos.LessonResponse;

import java.util.List;
import java.util.UUID;

public interface LessonService {
    LessonResponse create(LessonCreateRequest req);
    LessonResponse get(UUID id);
    List<LessonResponse> list();
    void delete(UUID id);
}