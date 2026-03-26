package edu.unimagdalena.lms.services;

import edu.unimagdalena.lms.api.dto.CourseDtos.CourseCreateRequest;
import edu.unimagdalena.lms.api.dto.CourseDtos.CourseResponse;

import java.util.List;
import java.util.UUID;

public interface CourseService {
    CourseResponse create(CourseCreateRequest req);
    CourseResponse get(UUID id);
    List<CourseResponse> list();
    void delete(UUID id);
}