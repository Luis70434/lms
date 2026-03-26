package edu.unimagdalena.lms.services;

import edu.unimagdalena.lms.api.dto.AssessmentDtos.AssessmentCreateRequest;
import edu.unimagdalena.lms.api.dto.AssessmentDtos.AssessmentResponse;

import java.util.List;
import java.util.UUID;

public interface AssessmentService {
    AssessmentResponse create(AssessmentCreateRequest req);
    AssessmentResponse get(UUID id);
    List<AssessmentResponse> list();
    void delete(UUID id);
}