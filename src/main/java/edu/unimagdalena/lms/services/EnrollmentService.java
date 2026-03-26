package edu.unimagdalena.lms.services;

import edu.unimagdalena.lms.api.dto.EnrollmentDtos.EnrollmentCreateRequest;
import edu.unimagdalena.lms.api.dto.EnrollmentDtos.EnrollmentResponse;

import java.util.List;
import java.util.UUID;

public interface EnrollmentService {
    EnrollmentResponse create(EnrollmentCreateRequest req);
    EnrollmentResponse get(UUID id);
    List<EnrollmentResponse> list();
    void delete(UUID id);
}