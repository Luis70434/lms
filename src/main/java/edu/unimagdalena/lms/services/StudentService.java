package edu.unimagdalena.lms.services;

import edu.unimagdalena.lms.api.dto.StudentDtos.StudentCreateRequest;
import edu.unimagdalena.lms.api.dto.StudentDtos.StudentResponse;

import java.util.List;
import java.util.UUID;

public interface StudentService {
    StudentResponse create(StudentCreateRequest req);
    StudentResponse get(UUID id);
    List<StudentResponse> list();
    void delete(UUID id);
}