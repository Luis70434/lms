package edu.unimagdalena.lms.api.dto;

import java.time.Instant;
import java.util.UUID;

public class EnrollmentDtos {


    public record EnrollmentCreateRequest(
            String status,
            UUID studentId,
            UUID courseId
    ) {}


    public record EnrollmentResponse(
            UUID id,
            String status,
            Instant enrolledAt
    ) {}
}