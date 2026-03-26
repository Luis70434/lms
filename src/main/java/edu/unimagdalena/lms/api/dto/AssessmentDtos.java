package edu.unimagdalena.lms.api.dto;

import java.time.Instant;
import java.util.UUID;

public class AssessmentDtos {


    public record AssessmentCreateRequest(
            String type,
            Integer score,
            UUID studentId,
            UUID courseId
    ) {}


    public record AssessmentResponse(
            UUID id,
            String type,
            Integer score,
            Instant takenAt
    ) {}
}