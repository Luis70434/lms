package edu.unimagdalena.lms.services;

import edu.unimagdalena.lms.api.dto.InstructorProfileDtos.InstructorProfileCreateRequest;
import edu.unimagdalena.lms.api.dto.InstructorProfileDtos.InstructorProfileResponse;

import java.util.List;
import java.util.UUID;

public interface InstructorProfileService {
    InstructorProfileResponse create(InstructorProfileCreateRequest req);
    InstructorProfileResponse get(UUID id);
    List<InstructorProfileResponse> list();
    void delete(UUID id);
}