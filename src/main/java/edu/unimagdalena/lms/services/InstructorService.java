package edu.unimagdalena.lms.services;

import edu.unimagdalena.lms.api.dto.InstructorDtos.InstructorCreateRequest;
import edu.unimagdalena.lms.api.dto.InstructorDtos.InstructorResponse;

import java.util.List;
import java.util.UUID;

public interface InstructorService {
    InstructorResponse create(InstructorCreateRequest req);
    InstructorResponse get(UUID id);
    List<InstructorResponse> list();
    void delete(UUID id);
}